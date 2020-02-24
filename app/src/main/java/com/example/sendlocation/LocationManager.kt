package com.example.sendlocation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.app.AlertDialog
import android.content.Intent
import android.location.LocationManager.GPS_PROVIDER




class LocationManager(var context: Context) {
    var oldLongitude: Double = 0.0
    var oldLatitude: Double = 0.0
    var newLongitude: Double = 0.0
    var newLatitude: Double = 0.0
    private var isFirsTime = true
    var locationManager:LocationManager? = null
     var locationListener: LocationListener? = null
    @SuppressLint("MissingPermission")
    fun getLocation(onSendSmsListener: (String) -> Unit){
        if(Permissions.checkFineLocationPermission(context)){
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationListener = object : LocationListener {

                override fun onLocationChanged(location: Location) {
                    if(isFirsTime){
                        oldLongitude = location.longitude
                        oldLatitude = location.latitude
                        isFirsTime = false
                    }
                    newLongitude = location.longitude
                    newLatitude =location.latitude

                    val distanceBetweenInMetro = FloatArray(3)
                    Location.distanceBetween(oldLatitude, oldLongitude, newLatitude, newLongitude,distanceBetweenInMetro)

                    if(distanceBetweenInMetro[0]>1.2 && distanceBetweenInMetro[0]<3){
                        val message = "El celular se movio ${distanceBetweenInMetro[0]} metros"
                        onSendSmsListener(message)
                    }
                    oldLongitude = newLongitude
                    oldLatitude = newLatitude

                }

                override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
                }

                override fun onProviderEnabled(provider: String) {
                }

                override fun onProviderDisabled(provider: String) {
                }
            }
            locationManager.requestLocationUpdates(GPS_PROVIDER, 0, 0.0f,locationListener)

        }

    }
    fun removeUpdate(){
        locationManager?.removeUpdates(locationListener)
    }
    @SuppressLint("MissingPermission")
    fun restartUpdate(){
        locationManager?.requestLocationUpdates(GPS_PROVIDER, 0, 0.0f,locationListener)
    }
    fun gpsStatusCheck() {
        val manager =context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (!manager.isProviderEnabled(GPS_PROVIDER)) {
            buildAlertMessageNoGps()
        }
    }

    private fun buildAlertMessageNoGps() {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(context.getString(R.string.gps_question))
            .setCancelable(false)
            .setPositiveButton(context.getString(R.string.yes)) { _, _ -> context.startActivity(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
            .setNegativeButton(context.getString(R.string.no)) { dialog, _ ->
                run {
                    val intentMain = Intent(context, MainActivity::class.java)
                    context.startActivity(intentMain)
                    dialog.cancel()
                }
            }
        val alert = builder.create()
        alert.show()
    }
}