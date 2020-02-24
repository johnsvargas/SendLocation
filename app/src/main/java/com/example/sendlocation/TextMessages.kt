package com.example.sendlocation

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager

class TextMessages(var context: Context, var message: String, var phoneNumber: String) {

    fun sendMessage(){
        val smsManager: SmsManager = SmsManager.getDefault()
        val partsOfMessage : ArrayList<String> = smsManager.divideMessage(message)
        val intentSmsSent = Intent(Constants.ACTION_SMS_SENT)
        val pendingIntentSent: PendingIntent = PendingIntent.getBroadcast(context, 0, intentSmsSent, 0)
        val intentSmsDelivered = Intent(Constants.ACTION_SMS_DELIVERED)
        val pendingIntentSmsDelivered = PendingIntent.getBroadcast(context, 0, intentSmsDelivered, 0)

        if (partsOfMessage.size == 1) {
            val messageToSend = partsOfMessage[0]
            smsManager.sendTextMessage(phoneNumber, null, messageToSend, pendingIntentSent, pendingIntentSmsDelivered)
        } else {
            val arrayPendingIntentSent = ArrayList<PendingIntent>()
            val arrayPendingIntentSmsDelivered = ArrayList<PendingIntent>()
            for (i in 0 until partsOfMessage.size) {
                arrayPendingIntentSent.add(i, pendingIntentSent)
                arrayPendingIntentSmsDelivered.add(i, pendingIntentSmsDelivered)
            }
            smsManager.sendMultipartTextMessage(phoneNumber, null, partsOfMessage, arrayPendingIntentSent, arrayPendingIntentSmsDelivered)
        }
    }
}