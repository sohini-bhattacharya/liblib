package com.example.seventhsense

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.ActivityRecognition
import com.google.android.gms.location.ActivityRecognitionClient

class Activity(context1: Context) {
    var context: Context = context1
    lateinit var client: ActivityRecognitionClient
    var csv: CSV = CSV(context)

    var status = false

    fun start(){
        client = ActivityRecognition.getClient(context)
        requestForUpdates()

        status = true
    }

    fun stop(){
        deregisterForUpdates()
        status = false
    }

    fun getStatus(): String {
        return status.toString()
    }

    @SuppressLint("MissingPermission")
    private fun requestForUpdates() {
        client
            .requestActivityUpdates(5000,getPendingIntent())
            .addOnSuccessListener {
                showToast("Successful registration")
            }
            .addOnFailureListener { e: Exception ->
                showToast("Unsuccessful registration")
            }
    }

    @SuppressLint("MissingPermission")
    private fun deregisterForUpdates() {
        client
            .removeActivityUpdates(getPendingIntent())
            .addOnSuccessListener {
                getPendingIntent().cancel()
                showToast("successful deregistration")
            }
            .addOnFailureListener { e: Exception ->
                showToast("unsuccessful deregistration")
            }
    }



    private fun getPendingIntent(): PendingIntent {
        Log.i("INT","INTENT")

        val intent = Intent(context, ActivityRecognitionReceiver::class.java)
        Log.i("INT","INTENT2")

        return PendingIntent.getBroadcast(
            context,
            Constants.REQUEST_CODE_INTENT_ACTIVITY_TRANSITION,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        Log.i("INT","INTENT3")

    }


    @SuppressLint("MissingPermission")
    private fun removeUpdates(){
        client.removeActivityUpdates(getPendingIntent())
    }


    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG)
            .show()
    }

}