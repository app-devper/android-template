package  com.devper.template.core.platform.location

import android.app.*
import android.content.Intent
import android.content.res.Configuration
import android.location.Location
import android.os.*
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.devper.template.BuildConfig
import com.google.android.gms.location.*
import timber.log.Timber

class LocationService : Service() {

    private val mBinder = LocalBinder()
    private var mChangingConfiguration = false
    private lateinit var mNotificationManager: NotificationManager
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private var mLocationCallback: LocationCallback
    private var mServiceHandler: Handler? = null
    private var mLocation: Location? = null
    private var mLocationRequest: LocationRequest? = null

    private val name: String
        get() {
            val appName: String
            val applicationInfo = this.applicationInfo
            val stringId = applicationInfo.labelRes
            appName = if (stringId == 0) {
                applicationInfo.nonLocalizedLabel.toString()
            } else {
                this.getString(stringId)
            }
            return appName
        }

    private val mainActivityClass: Class<*>?
        get() {
            val packageName = packageName
            val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
            val className = launchIntent?.component?.className
            return try {
                Class.forName(className!!)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

    private val notification: Notification
        get() {
            return NotificationCompat.Builder(this, CHANNEL_ID).run {
                var intentClass = mainActivityClass
                if (currentClass != null) {
                    intentClass = currentClass as Class<*>
                }
                val pendingIntent = PendingIntent.getActivity(
                    applicationContext, 0, Intent(applicationContext, intentClass), PendingIntent.FLAG_UPDATE_CURRENT
                )
                setContentIntent(pendingIntent)
                setContentText("On Tracking GPS")
                setContentTitle(name)
                setOngoing(true)
                priority = NotificationCompat.PRIORITY_HIGH
                setSound(null)
                setSmallIcon(android.R.drawable.ic_menu_mylocation)
                setWhen(System.currentTimeMillis())
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    setChannelId(CHANNEL_ID)
                }
                build()
            }
        }

    init {
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                locationResult?.let {
                    onNewLocation(it.lastLocation)
                }
            }
        }
    }

    override fun onCreate() {
        Timber.i("Service onCreate")
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        createLocationRequest()
        getLastLocation()

        val handlerThread = HandlerThread(TAG)
        handlerThread.start()
        mServiceHandler = Handler(handlerThread.looper)
        mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Android O requires a Notification Channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the channel for the notification
            val mChannel = NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_LOW)
            // Set the Notification Channel for the Notification Manager.
            mNotificationManager.createNotificationChannel(mChannel)
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Timber.i("Service started")
        val startedFromNotification = intent.getBooleanExtra(EXTRA_STARTED_FROM_NOTIFICATION, false)

        // We got here because the user decided to remove location updates from the notification.
        if (startedFromNotification) {
            removeLocationUpdates()
            stopSelf()
        }
        // Tells the system to not try to recreate the service after it has been killed.
        return START_NOT_STICKY
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mChangingConfiguration = true
    }

    override fun onBind(intent: Intent): IBinder? {
        Timber.i("in onBind()")
        stopForeground(true)
        mChangingConfiguration = false
        return mBinder
    }

    override fun onRebind(intent: Intent) {
        Timber.i("in onRebind()")
        stopForeground(true)
        mChangingConfiguration = false
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent): Boolean {
        Timber.i("Last client unbound from service")
        if (!mChangingConfiguration && LocationHelper.requestingLocationUpdates(this)) {
            Timber.i("Starting foreground service")
            startForeground(NOTIFICATION_ID, notification)
        }
        return true // Ensures onRebind() is called when a client re-binds.
    }

    override fun onDestroy() {
        mServiceHandler?.removeCallbacksAndMessages(null)
    }

    fun requestLocationUpdates(locationRequest: LocationRequest?) {
        locationRequest?.let {
            mLocationRequest = it
        }
        Timber.i("Requesting location updates")
        LocationHelper.setRequestingLocationUpdates(this, true)
        startService(Intent(applicationContext, LocationService::class.java))
        try {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
        } catch (unlikely: SecurityException) {
            LocationHelper.setRequestingLocationUpdates(this, false)
            Timber.e("Lost location permission. Could not request updates. $unlikely")
        }
    }

    private fun removeLocationUpdates() {
        Timber.i("Removing location updates")
        try {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback)
            LocationHelper.setRequestingLocationUpdates(this, false)
            stopSelf()
        } catch (unlikely: SecurityException) {
            LocationHelper.setRequestingLocationUpdates(this, true)
            Timber.e("Lost location permission. Could not remove updates. $unlikely")
        }
    }

    private fun getLastLocation() {
        try {
            mFusedLocationClient.lastLocation?.addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    mLocation = task.result
                } else {
                    Timber.w("Failed to get location.")
                }
            }
        } catch (unlikely: SecurityException) {
            Timber.e("Lost location permission.$unlikely")
        }
    }

    private fun onNewLocation(location: Location) {
        Timber.i("New location: $location")
        mLocation = location
        val intent = Intent(ACTION_BROADCAST)
        intent.putExtra(EXTRA_LOCATION, location)
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
    }

    private fun createLocationRequest() {
        mLocationRequest = LocationRequest().apply {
            interval = UPDATE_INTERVAL_IN_MILLISECONDS
            fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    fun setCurrentClass(current: Class<*>?) {
        currentClass = current
    }

    inner class LocalBinder : Binder() {
        val service: LocationService
            get() = this@LocationService
    }

    companion object {
        private const val PACKAGE_NAME = BuildConfig.APPLICATION_ID
        private val TAG = LocationService::class.java.simpleName
        private const val CHANNEL_ID = "location_01"
        private var currentClass: Class<*>? = null
        const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000
        const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2
        private const val NOTIFICATION_ID = 12345678

        const val ACTION_BROADCAST = "$PACKAGE_NAME.broadcast"
        const val EXTRA_LOCATION = "$PACKAGE_NAME.extra"
        const val EXTRA_STARTED_FROM_NOTIFICATION = "$PACKAGE_NAME.started_from_notification"
    }
}