package com.example.sendlocation

import android.content.pm.PackageManager
import android.Manifest.permission
import android.app.Activity
import android.content.Context
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat



object Permissions {
    const val FINE_LOCATION_PERMISSION = 100
    const val SEND_SMS_PERMISSION = 101

    fun checkFineLocationPermission (context:Context): Boolean{
        return ContextCompat.checkSelfPermission(context, permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
    fun checkSendSMSPermission (context:Context): Boolean{
        return ContextCompat.checkSelfPermission(context, permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED
    }

    fun requestFineLocationPermission(activity: Activity){
        ActivityCompat.requestPermissions(activity, arrayOf(permission.ACCESS_FINE_LOCATION),
            FINE_LOCATION_PERMISSION)
    }

    fun requestSendSMSPermission(activity: Activity){
        ActivityCompat.requestPermissions(activity, arrayOf(permission.SEND_SMS),
            SEND_SMS_PERMISSION)
    }
}