package  com.devper.template.core.platform.location

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*

@SuppressLint("LogNotTimber")
class SingleLocationService(private val mActivity: Activity) {

    var callback: (location: Location?) -> Unit = {}
    private var mLocationPermissionGranted: Boolean = false
    private var mFusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mActivity)
    private var mLocationRequest: LocationRequest? = null
    var mLocation: Location? = null

    private var mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(location: LocationResult?) {
            super.onLocationResult(location)
            mLocation = location?.lastLocation
            callback.invoke(mLocation)
            removeLocationUpdates()
        }
    }

    init {
        createLocationRequest()
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

     fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(mActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true
            requestLocationUpdates()
        } else {
            ActivityCompat.requestPermissions(mActivity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }

    private fun createLocationRequest() {
        mLocationRequest = LocationRequest().apply {
            interval = UPDATE_INTERVAL_IN_MILLISECONDS
            fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    fun setLocationRequest(locationRequest: LocationRequest) {
        this.mLocationRequest = locationRequest
    }

    companion object {
        private val TAG = SingleLocationService::class.java.simpleName
        const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 248
        const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000
        const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2
    }

}
