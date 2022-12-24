package com.example.seventhsense

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.util.Log
import java.util.*

class IDTest(context1: Context) {
    var uniqueID: String = ""
    var android_id: String = ""
    var context: Context = context1

    var csv: CSVTest = CSVTest(context)

    @SuppressLint("HardwareIds")
    fun getUUId(){
        uniqueID = UUID.randomUUID().toString()

        csv.record("$uniqueID","ID")

//        return uniqueID
    }


    fun getAndroidId(){
        // main
        android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)

        Log.i("ID","$android_id")
        csv.record("$android_id","ID")

//        return android_id
    }

}