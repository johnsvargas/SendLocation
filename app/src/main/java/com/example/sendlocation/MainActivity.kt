package com.example.sendlocation

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    var hasSendSMSPermission: Boolean = false
    var hasFineLocationPermission: Boolean = false
    var context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermissions()
        getPermissions()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            Permissions.SEND_SMS_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    hasSendSMSPermission = true
                    getPermissions()
                } else {
                    hasSendSMSPermission = false
                    return
                }
            }
            Permissions.FINE_LOCATION_PERMISSION->{
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    hasFineLocationPermission = true
                    getPermissions()
                }else{
                    hasFineLocationPermission = false

                    return
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun checkPermissions(){
        hasSendSMSPermission = Permissions.checkSendSMSPermission(context)
        hasFineLocationPermission = Permissions.checkFineLocationPermission(context)
    }

    fun getPermissions(){
        if(!hasSendSMSPermission){
            Permissions.requestSendSMSPermission(this)
        }
        if (!hasFineLocationPermission){
            Permissions.requestFineLocationPermission(this)
        }
    }
}
