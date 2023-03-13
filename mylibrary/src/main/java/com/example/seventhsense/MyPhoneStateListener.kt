package com.example.seventhsense

import android.content.Context
import android.os.Build
import android.telephony.PhoneStateListener
import android.telephony.SignalStrength
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

var mSignalStrength = 0
@Suppress("DEPRECATION")
class MyPhoneStateListener(context: Context) : PhoneStateListener() {

    var csv: CSVTest = CSVTest(context)

    override fun onSignalStrengthsChanged(signalStrength: SignalStrength) {
        super.onSignalStrengthsChanged(signalStrength)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            mSignalStrength = signalStrength.gsmSignalStrength
            mSignalStrength = (2 * mSignalStrength) - 113 // -> dBm

            var ss = mSignalStrength.toString()

//            csv.record(mSignalStrength.toString(), "cell")

            Log.i("HERE", mSignalStrength.toString())

            var resultList = signalStrength.cellSignalStrengths
            for (result in resultList) {
                var text = "${ss},${result.dbm},${result.asuLevel},${result.level}"

                csv.record(
                    "${text}" + "," + SimpleDateFormat("HH:mm:ss", Locale.US).format(
                        Date()
                    ), "cellInfo"
                )
                Log.i("BACK", "$text")
//                Thread.sleep(10000)
            }


        }

    }
}