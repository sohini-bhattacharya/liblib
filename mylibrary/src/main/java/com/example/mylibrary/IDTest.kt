package com.example.mylibrary

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import java.util.*

class IDTest(context1: Context) {
    var uniqueID: String = ""
    var android_id: String = ""
    var context: Context = context1

    var csv: CSVTest = CSVTest(context)

    @SuppressLint("HardwareIds")
    fun getUUId(): String{
        uniqueID = UUID.randomUUID().toString()

        csv.record("$uniqueID","ID")

        return uniqueID
    }

    fun getAndroidId(): String{
        android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)

        csv.record("$android_id","ID")

        return android_id
    }

}