package com.mk.contactsmap.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.location.*

class LocationClient(private val fusedLocationProviderClient: FusedLocationProviderClient) {

    private lateinit var locationCallback: LocationCallback
    companion object{
        const val LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION
        private const val GPS_UPDATE_INTERVAL = 500L
        fun checkLocationEnabled(context: Context): Boolean {
            val locationManager =
                context.getSystemService(android.content.Context.LOCATION_SERVICE) as LocationManager
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            )
        }

        fun checkLocationPermission(context: Context): Boolean {
            return context.checkSelfPermission(LOCATION_PERMISSION) == PackageManager.PERMISSION_GRANTED
        }

    }

    /**
     * @param oneShot -> boolean value if true gets the location once and stops location updates, if false updates location until the stopLocationUpdates() i is invoked
     * @param onSuccess -> callback lamda invoked when location is obtained
     * this function fetch location only once
     * */
    fun getCurrentLocationUpdates(context: Context,oneShot:Boolean,onSuccess:(location:Location)->Unit){
        //check location is accessible
        if((checkLocationPermission(context) && checkLocationEnabled(context)).not())
            throw Exception("check location permissions and location status on / off")

        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, GPS_UPDATE_INTERVAL).build()
        if(::locationCallback.isInitialized.not())
        {
            locationCallback = object :LocationCallback(){
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    onSuccess(locationResult.locations[0])
                    if(oneShot)
                        stopLocationUpdates()
                }
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null)

    }

    fun stopLocationUpdates(){
        if(::locationCallback.isInitialized)
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}