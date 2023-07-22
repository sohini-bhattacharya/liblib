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

class Compass(context: Context) : SensorEventListener {


    private lateinit var powerManager: PowerManager
    private lateinit var wakeLock: PowerManager.WakeLock

    private lateinit var orient_manager: SensorManager

    lateinit var outputWriter: OutputStreamWriter
    lateinit var fileOutputStream: FileOutputStream
    var context1: Context = context

    var currentDegree = 0f

    var csv: CSV = CSV(context1)

    var status: Boolean = false
    var all: String = "h"

    fun getSensor() {
        Toast.makeText(context1, "Sensor Started", Toast.LENGTH_LONG).show();

        powerManager = context1?.getSystemService(Service.POWER_SERVICE) as PowerManager

        wakeLock = powerManager.newWakeLock(
            PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK,
            "MyApp::MyWakelockTag"
        )
        wakeLock.acquire()
        orient_manager = context1.getSystemService(AppCompatActivity.SENSOR_SERVICE) as SensorManager

        orient_manager.registerListener(this, orient_manager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_GAME)

        status = true

    }

    fun stop(){

        orient_manager.unregisterListener(this)

        wakeLock.release()
        Toast.makeText(context1, "Sensor Stopped", Toast.LENGTH_LONG).show()
        Log.i("HERE", "STOPPED")

        status = false
    }


    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ORIENTATION) {

            val degree = Math.round(event?.values?.get(0)!!)

            currentDegree = (-degree).toFloat()

            Log.i("ori","${currentDegree}")
        }

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }


}