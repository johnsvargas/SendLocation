package com.example.sendlocation

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

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

    fun onClickButtonEnviar(){
        btnEnviar.setOnClickListener {
            if (editTextPhone.text.isNullOrEmpty() || editTextPhone.text.isNullOrBlank()){
                Toast.makeText(context,"No puede estar vacio el número telefonico",Toast.LENGTH_SHORT).show()
            }else{
                goToLocationActivity(editTextPhone.text.toString())
            }
        }
    }

    fun goToLocationActivity(phoneNumber: String){
        val intentLocationActivity = Intent(context,LocationActivity::class.java)
        intentLocationActivity.putExtra(Constants.PHONE_NUMBER,phoneNumber)
        startActivity(intentLocationActivity)
        finish()
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

    fun checkPermissions():Boolean{
        hasSendSMSPermission = Permissions.checkSendSMSPermission(context)
        hasFineLocationPermission = Permissions.checkFineLocationPermission(context)
        return hasSendSMSPermission && hasFineLocationPermission
    }

    fun getPermissions(){
        if(!hasSendSMSPermission){
            Permissions.requestSendSMSPermission(this)
        }
        if (!hasFineLocationPermission){
            Permissions.requestFineLocationPermission(this)
        }
        onClickButtonEnviar()
    }
}
