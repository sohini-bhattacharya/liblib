package com.example.mylibrary

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import java.util.*

class IDTest(context1: Context) {
    var uniqueID: String = ""
    var android_id: String = ""
    var context: Context = context1

    @SuppressLint("HardwareIds")
    fun getId(): String{
        uniqueID = UUID.randomUUID().toString()
        android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)

        return uniqueID
    }


}