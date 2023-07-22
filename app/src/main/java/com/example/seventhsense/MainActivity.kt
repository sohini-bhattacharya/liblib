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

    //    lateinit var s: SensorTest
//    lateinit var b: BLETest
//    lateinit var p: PredictTest
    lateinit var pe: PermissionTest

    //    lateinit var w: WifiTest
//    lateinit var id: IDTest
    lateinit var loc: LocationTest
    lateinit var baro: BarometerTest
//    lateinit var magneto: MagnetoTest

    //    lateinit var a: ActivityTest
    lateinit var ce: CellTest

    lateinit var pa: PressureAltTest

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
//        id = IDTest(this)
        pe = PermissionTest(applicationContext, this)
//        w = WifiTest(this)

//        a = ActivityTest(this)
//        p = PredictTest(this)

        pa = PressureAltTest(applicationContext, this)
//        loc = LocationTest(applicationContext,this)

//        baro = BarometerTest(applicationContext,this)

        ce = CellTest(applicationContext)

//        magneto = MagnetoTest(applicationContext)

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

//        p = PredictTest(this)


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




