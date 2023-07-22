package com.example.seventhsense

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.LocationManager
import android.os.PowerManager
import android.util.Log
import android.widget.Toast
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.*

//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

//import androidx.core.content.ContextCompat.getSystemService

@SuppressLint("NewApi")
class Sensor(context: Context) : SensorEventListener  {
    lateinit var outputWriter: OutputStreamWriter
    lateinit var fileOutputStream: FileOutputStream
    var context1: Context = context
    var light = 0.0f
    var proxi = 0.0f
    var pressure = 0.0f
    var roll = 0.0f
    var yaw = 0.0f
    var pitch = 0.0f
    var x = 0.0f
    var y = 0.0f
    var z = 0.0f

    var csv: CSV = CSV(context1)

    //    private lateinit var text: TextView
    var status: Boolean = false
    var all: String = "h"

//    private var _all: String by Delegates.observable(all) { property, oldValue, newValue ->
//        all = newValue
//    }


    private lateinit var powerManager: PowerManager
    private lateinit var wakeLock: PowerManager.WakeLock

    private lateinit var light_manager: SensorManager
    private var light_sensor: Sensor? = null

    private lateinit var proxi_manager: SensorManager
    private var proxi_sensor: Sensor? = null

    private lateinit var mag_manager: SensorManager
    private var mag_sensor: Sensor? = null

    private lateinit var orient_manager: SensorManager
    private var orient_sensor: Sensor? = null

    private lateinit var pressure_manager: SensorManager
    private var pressure_sensor: Sensor? = null

    private lateinit var location_manager: LocationManager

//    lateinit var builder: NotificationCompat.Builder


    fun getSensor(){
        Toast.makeText(context1, "Sensor Started", Toast.LENGTH_LONG).show();

        powerManager = context1?.getSystemService(Service.POWER_SERVICE) as PowerManager

        wakeLock = powerManager.newWakeLock(
            PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK,
            "MyApp::MyWakelockTag"
        )
        wakeLock.acquire()

        mag_manager = context1?.getSystemService(SENSOR_SERVICE) as SensorManager
        mag_sensor = mag_manager!!.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        orient_manager = context1?.getSystemService(SENSOR_SERVICE) as SensorManager
        orient_sensor = orient_manager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION)

        light_manager = context1?.getSystemService(SENSOR_SERVICE) as SensorManager
        light_sensor = light_manager!!.getDefaultSensor(Sensor.TYPE_LIGHT)

        proxi_manager = context1?.getSystemService(SENSOR_SERVICE) as SensorManager
        proxi_sensor = proxi_manager!!.getDefaultSensor(Sensor.TYPE_PROXIMITY)


        pressure_manager = context1?.getSystemService(SENSOR_SERVICE) as SensorManager
        pressure_sensor = pressure_manager!!.getDefaultSensor(Sensor.TYPE_PRESSURE)

        light_manager.registerListener(this, light_sensor, SensorManager.SENSOR_DELAY_FASTEST)
        proxi_manager.registerListener(this, proxi_sensor, SensorManager.SENSOR_DELAY_FASTEST)

        pressure_manager.registerListener(this, pressure_sensor, 5000000)

        mag_manager.registerListener(this, mag_sensor, SensorManager.SENSOR_DELAY_FASTEST)
        orient_manager.registerListener(this, orient_sensor, SensorManager.SENSOR_DELAY_FASTEST)

        status = true

//        return START_REDELIVER_INTENT

    }

//    fun csv(str: String) {
//        try {
//            fileOutputStream =
//                context1.applicationcontext1.openFileOutput("TEST123.txt", context1.MODE_APPEND)
//            outputWriter = OutputStreamWriter(fileOutputStream)
//            outputWriter.write(str + "\n")
//            outputWriter.close()
//
//        } catch (e: IOException) {
//        }
//    }

    fun stop(){
        light_manager.unregisterListener(this)
        proxi_manager.unregisterListener(this)
        mag_manager.unregisterListener(this)
        pressure_manager.unregisterListener(this)
        orient_manager.unregisterListener(this)
        wakeLock.release()
        Toast.makeText(context1, "Sensor Stopped", Toast.LENGTH_LONG).show()
        Log.i("HERE", "STOPPED")

        status = false
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onSensorChanged(event: SensorEvent?) {

        if (event?.sensor?.type == Sensor.TYPE_PRESSURE) {
            pressure = event.values[0]

//            pressure_text.text = pressure.toString()

//      x = gps altitude
//      y = pressure
//      y=f(x)

        }

        if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
            light = event.values[0]


//            light_text.text = light.toString()

            Log.v("LIGHT", "${System.currentTimeMillis()},${light}")
//            csvlight("${System.currentTimeMillis()},${light}")

//
        }

        if (event?.sensor?.type == Sensor.TYPE_PROXIMITY) {
            proxi = event.values[0]




        }

        if (event?.sensor?.type == Sensor.TYPE_MAGNETIC_FIELD) {
            x = event.values[0]
            y = event.values[1]
            z = event.values[2]


//            Log.v("MAG", "${System.currentTimeMillis()},${x},${y},${z}")
//            csvlight("${System.currentTimeMillis()},${light}")


        }

        if (event?.sensor?.type == Sensor.TYPE_ORIENTATION) {
            yaw = event.values[0]
            pitch = event.values[1]
            roll = event.values[2]


//            Log.v("ORIENT", "${System.currentTimeMillis()},${yaw},${pitch},${roll}")
//            csvlight("${System.currentTimeMillis()},${light}")


        }

        all = "${System.currentTimeMillis()},${SimpleDateFormat("HH", Locale.US).format(Date())}," +
                "${proxi},${light},${yaw},${pitch},${roll},${x},${y},${z},${pressure}"

        csv.record(all,"SENSOR")

        Log.i("ALL", all)
////

    //        }
    }

//    fun updateAll(string: String): String {
//        all = string
//        return string
//
//    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }


}