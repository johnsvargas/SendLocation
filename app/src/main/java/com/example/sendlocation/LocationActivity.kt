package com.example.sendlocation

import android.app.NotificationManager
import android.content.Context
import android.media.MediaPlayer
import android.media.RingtoneManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_location.*

class LocationActivity : AppCompatActivity(){
    fun sendSms(message: String) {
        if(smsToSend == null){
            smsToSend = TextMessages(context,message,phoneNumber)
        }else{
            smsToSend?.message = message
        }
        smsToSend?.sendMessage()

        val alarmSound = RingtoneManager.getDefaultUri (RingtoneManager. TYPE_NOTIFICATION )
        val mp : MediaPlayer = MediaPlayer.create(applicationContext, alarmSound)
        mp.start()
    }

    lateinit var phoneNumber: String
    var context = this
    var hasSendSMSPermission: Boolean = false
    var hasFineLocationPermission: Boolean = false
    var smsToSend :TextMessages? = null
   var locationManager: LocationManager? = null
    //private var message = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        intent.extras?.getString(Constants.PHONE_NUMBER,"5525188210")?.let {
            phoneNumber = "+52$it"
            hasSendSMSPermission = Permissions.checkSendSMSPermission(context)
            hasFineLocationPermission = Permissions.checkFineLocationPermission(context)
            txtPhoneNumber.text = "El Telefono es : $phoneNumber"
            locationManager =  LocationManager(context)
            locationManager?.gpsStatusCheck()
            locationManager?.getLocation { item ->sendSms(item) }
        }
    }
    override fun onResume() {
        super.onResume()
        if(locationManager != null)
            locationManager?.restartUpdate()
    }

    override fun onPause() {
        super.onPause()
        if(locationManager != null)
            locationManager?.removeUpdate()
    }
}
