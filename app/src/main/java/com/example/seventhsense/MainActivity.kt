package com.example.seventhsense

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.TextView
import com.example.seventhsense.R.id.permission


class MainActivity : AppCompatActivity() {

    lateinit var text: TextView

    //    lateinit var s: Sensor
//    lateinit var b: BLE
//    lateinit var p: PredictIO
    lateinit var pe: Permissions

    //    lateinit var w: Wifi
//    lateinit var id: ID
    lateinit var loc: Location
    lateinit var baro: Barometer
//    lateinit var magneto: Magneto

    //    lateinit var a: Activity
    lateinit var ce: Cell

    lateinit var pa: PressureAltitude

    lateinit var permissions: Switch
    lateinit var button: Switch

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permissions = findViewById(permission)
        button = findViewById(R.id.button_status)
        text = findViewById(R.id.txt)
//
//        id = ID(this)
        pe = Permissions(applicationContext, this)
//        w = Wifi(this)

//        a = Activity(this)
//        p = PredictIO(this)

        pa = PressureAltitude(applicationContext, this)
//        loc = Location(applicationContext,this)

//        baro = Barometer(applicationContext,this)

        ce = Cell(applicationContext)

//        magneto = Magneto(applicationContext)

        pe.getPermissions()


//        baro.getBarometer()


//        permissions.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
//                ) {
//
//
//                } else {
////                    requestForUpdates()
//                }
//            } else {
//
////                deregisterForUpdates()
//            }
//        }

//        p = PredictIO(this)


        button.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
                ) {
//                    a.start()
//                    s.start()
//                    b.start()
//                    Log.i("uni","${id.getAndroidId()}")
//                    Log.i("uni","${id.getUUId()}")

//                    pe.getPrediction()
//                    baro.getBarometer()

                    pa.getCurrentLocation()
//                    ce.getCell()

//                    magneto.getSensor()
//                    loc.getCurrentLocation()

//                    w.getWifi()
//                    text.text = w.getStatus()


                } else {
//                    requestForUpdates()
                }
            } else {
//                s.stop()
//                b.stop()

//                pe.stop()
//                a.stop()
//                w.stop()
                pa.stop()
//                baro.stop()
//                magneto.stop()
//                text.text = w.getStatus()
//                deregisterForUpdates()
            }
        }


//        s.stop(this)

    }
}




