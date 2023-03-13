package com.example.seventhsense

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.util.Log
import java.util.*

class IDTest(context: Context) {
    var uniqueID: String = ""
    var android_id: String = ""
    var context1: Context = context

    var csv: CSVTest = CSVTest(context1)

    @SuppressLint("HardwareIds")
    fun getUUId(){
        uniqueID = UUID.randomUUID().toString()

        csv.record("$uniqueID","ID")

//        return uniqueID
    }


    fun getAndroidId(){
        // main
        android_id = Settings.Secure.getString(context1.getContentResolver(), Settings.Secure.ANDROID_ID)

        Log.i("ID","$android_id")
        csv.record("$android_id","ID")

//        return android_id
    }

}