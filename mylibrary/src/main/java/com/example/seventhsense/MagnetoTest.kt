package com.example.seventhsense

import android.app.Service
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.PowerManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class MagnetoTest(context1: Context) : SensorEventListener {

    lateinit var outputWriter: OutputStreamWriter
    lateinit var fileOutputStream: FileOutputStream
    var context: Context = context1

    var csv: CSVTest = CSVTest(context)

    //    private lateinit var text: TextView
    var status: Boolean = false
    var all: String = "h"

    var roll = 0.0f
    var yaw = 0.0f
    var pitch = 0.0f

    var valuex: Float = 0.0f
    var valuey: Float = 0.0f
    var valuez: Float = 0.0f

    var valuex_un: Float = 0.0f
    var valuey_un: Float = 0.0f
    var valuez_un: Float = 0.0f

    var currentDegree = 0f

    private lateinit var powerManager: PowerManager
    private lateinit var wakeLock: PowerManager.WakeLock

    private lateinit var mag_manager: SensorManager
    private var mag_sensor: Sensor? = null

    private lateinit var mag_uncal_manager : SensorManager
    private var mag_uncal_sensor: Sensor? = null

    private lateinit var orient_manager: SensorManager
    private var orient_sensor: Sensor? = null

    fun getSensor() {
        Toast.makeText(context, "Sensor Started", Toast.LENGTH_LONG).show();

        powerManager = context?.getSystemService(Service.POWER_SERVICE) as PowerManager

        wakeLock = powerManager.newWakeLock(
            PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK,
            "MyApp::MyWakelockTag"
        )
        wakeLock.acquire()
        mag_manager = context.getSystemService(AppCompatActivity.SENSOR_SERVICE) as SensorManager
        mag_sensor = mag_manager!!.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        mag_uncal_manager = context.getSystemService(AppCompatActivity.SENSOR_SERVICE) as SensorManager
        mag_uncal_sensor = mag_manager!!.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED)

        orient_manager = context?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        orient_sensor = orient_manager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION)

        mag_manager.registerListener(this, mag_sensor, SensorManager.SENSOR_DELAY_FASTEST)
        mag_uncal_manager.registerListener(this, mag_uncal_sensor, SensorManager.SENSOR_DELAY_FASTEST)
        orient_manager.registerListener(this, orient_sensor, SensorManager.SENSOR_DELAY_FASTEST)



        status = true

    }


        override fun onSensorChanged(event: SensorEvent?) {

            if (event?.sensor?.type == Sensor.TYPE_MAGNETIC_FIELD) {
                valuex = event.values[0]
                valuey = event.values[1]
                valuez = event.values[2]

            }

            if (event?.sensor?.type == Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED) {
                valuex_un = event.values[0]
                valuey_un = event.values[1]
                valuez_un = event.values[2]

            }

            if (event?.sensor?.type == Sensor.TYPE_ORIENTATION) {
                yaw = event.values[0]
                pitch = event.values[1]
                roll = event.values[2]

                val degree = Math.round(event?.values?.get(0)!!)

                currentDegree = (-degree).toFloat()

                Log.i("Orient","${currentDegree}")

            }


            all = "${System.currentTimeMillis()},${
                SimpleDateFormat("HH", Locale.US).format(Date())}"+"${currentDegree}"+"${valuex},${valuey},${valuez}"+
                    "${valuex_un},${valuey_un},${valuez_un},${yaw},${pitch},${roll}"

            csv.record(all,"MAGNETO")

        }


    fun stop(){
        mag_manager.unregisterListener(this)
        mag_uncal_manager.unregisterListener(this)
        orient_manager.unregisterListener(this)

        wakeLock.release()
        Toast.makeText(context, "Sensor Stopped", Toast.LENGTH_LONG).show()
        Log.i("HERE", "STOPPED")

        status = false
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }
}