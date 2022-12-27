package com.example.seventhsense

import android.app.Service
import android.content.Context
import android.hardware.SensorEventListener
import android.net.wifi.WifiManager
import android.os.Build
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager

@Suppress("DEPRECATION")
class CellTest(context1: Context) {

    var context: Context = context1

    var mTelephonyManager: TelephonyManager? = null
    var mPhoneStatelistener: MyPhoneStateListener? = null

    var status: Boolean = false


    fun getCell(){
        mPhoneStatelistener = MyPhoneStateListener(context)
        mTelephonyManager = context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        status = true

        mTelephonyManager!!.listen(mPhoneStatelistener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS)


    }

//    fun stopCell(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            mTelephonyManager!!.unregisterTelephonyCallback(mPhoneStatelistener)
//        }
//
//    }

//    fun listenCell(){
//
//    }


}