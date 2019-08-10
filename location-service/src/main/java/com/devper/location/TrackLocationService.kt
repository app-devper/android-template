package  com.devper.location

import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.location.*

class TrackLocationService(private val mActivity: Activity, lifecycle: Lifecycle, callBack: LocationCallBack) : LifecycleObserver {

    private var mLocationPermissionGranted: Boolean = false
    private var mFusedLocationClient: FusedLocationProviderClient
    private var mLocationRequest: LocationRequest? = null
    var mLocation: Location? = null

    private var mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(location: LocationResult?) {
            super.onLocationResult(location)
            mLocation = location!!.lastLocation
            callBack.onLocationResult(mLocation)
        }
    }

    init {
        lifecycle.addObserver(this)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mActivity)
        createLocationRequest()
        getLocationPermission()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun start() {
        if (mLocationPermissionGranted) {
            requestLocationUpdates()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun stop() {
        removeLocationUpdates()
    }

    private fun requestLocationUpdates() {
        Log.i(TAG, "Requesting location updates")
        try {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
        } catch (unlikely: SecurityException) {
            Log.e(TAG, "Lost location permission. Could not request updates. $unlikely")
        }
    }

    private fun removeLocationUpdates() {
        Log.i(TAG, "Removing location updates")
        try {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback)
        } catch (unlikely: SecurityException) {
            Log.e(TAG, "Lost location permission. Could not remove updates. $unlikely")
        }
    }

    private fun getLastLocation() {
        try {
            mFusedLocationClient.lastLocation.addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    mLocation = task.result
                } else {
                    Log.w(TAG, "Failed to get location.")
                }
            }
        } catch (unlikely: SecurityException) {
            Log.e(TAG, "Lost location permission.$unlikely")
        }
    }

    fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray) {
        mLocationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                when {
                    grantResults.isEmpty() -> // If user interaction was interrupted, the permission request is cancelled and you
                        Log.i(TAG, "User interaction was cancelled.")
                    grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                        // Permission was granted.
                        mLocationPermissionGranted = true
                        requestLocationUpdates()
                    }
                    else -> {

                    }
                }
            }
        }
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(mActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(mActivity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }

    private fun createLocationRequest() {
        mLocationRequest = LocationRequest().apply {
            interval = LocationService.UPDATE_INTERVAL_IN_MILLISECONDS
            fastestInterval = LocationService.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    fun setLocationRequest(locationRequest: LocationRequest) {
        this.mLocationRequest = locationRequest
    }

    companion object {
        private val TAG = TrackLocationService::class.java.simpleName
        const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 248
    }

}
