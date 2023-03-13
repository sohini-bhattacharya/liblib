package com.example.seventhsense

import android.app.Service
import android.content.Context
import android.hardware.SensorEventListener
import android.net.wifi.WifiManager
import android.os.Build
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager

@Suppress("DEPRECATION")
class CellTest(context: Context) {

    var context1: Context = context

    var mTelephonyManager: TelephonyManager? = null
    var mPhoneStatelistener: MyPhoneStateListener? = null

    var status: Boolean = false


    fun getCell(){
        mPhoneStatelistener = MyPhoneStateListener(context1)
        mTelephonyManager = context1?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

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