package com.example.mylibrary

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationManager
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.io.FileOutputStream
import java.io.OutputStreamWriter

class BarometerTest(context1: Context, activity1: AppCompatActivity) : SensorEventListener {

    lateinit var outputWriter: OutputStreamWriter
    lateinit var fileOutputStream: FileOutputStream
    var context: Context = context1

    var roll = 0.0f
    var yaw = 0.0f
    var pitch = 0.0f

    var pressure: Float = 0.0f

    var latitude: String = ""
    var longitude: String = ""
    var altitude: String = ""
    var bearing: String = ""
    var loc: String = ""
    var mAlt: String = ""

    var iobar: Int = 0

    lateinit var iotest: PredictTest

    var activity: Activity = activity1

    var fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)

    private lateinit var powerManager: PowerManager
    private lateinit var wakeLock: PowerManager.WakeLock

    private lateinit var mag_manager: SensorManager
    private var mag_sensor: Sensor? = null

    private lateinit var mag_uncal_manager : SensorManager
    private var mag_uncal_sensor: Sensor? = null

    private lateinit var orient_manager: SensorManager
    private var orient_sensor: Sensor? = null

    var csv: CSVTest = CSVTest(context)

    var status: Boolean = false
    var all: String = "h"


    @SuppressLint("MissingPermission")
    fun getBarometer() {

        iotest = PredictTest(context)

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

    fun isLocationEnabled(): Boolean {
        val manager: LocationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            buildAlertMessageNoGps()
        }

        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER)||manager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)||manager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)

    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(){


        if(isLocationEnabled()) {

            fusedLocationProviderClient.lastLocation.addOnCompleteListener { task->
                val location: Location?=task.result
                if(location==null){
//                    Toast.makeText(context,"Null", Toast.LENGTH_SHORT).show()
                }
                else{
//                    Toast.makeText(context,"Success LOC", Toast.LENGTH_SHORT).show()

                    loc = location.toString()
                    Log.i("LOCATION","$location")
                    Log.i("LOCATION","${location.latitude}")
                    Log.i("LOCATION","${location.longitude}")
                    Log.i("LOCATION","${location.altitude}")
                    Log.i("LOCATION","${location.bearing}")

                    latitude = location.latitude.toString()
                    longitude = location.longitude.toString()
                    altitude = location.altitude.toString()
                    bearing = location.bearing.toString()



                    if(iotest.final=="OUTDOOR") {

                        csv.record(
                            "${iotest.final},$latitude,$longitude,$bearing,$pressure,$mAlt",
                            "LOCATION"
                        )
                    }
                    else if(iotest.final=="INDOOR") {

                        csv.record(
                            "${iotest.final},$latitude,$longitude,$altitude,$bearing,$pressure",
                            "LOCATION"
                        )
                    }

                }


            }
        }
        else{
            Toast.makeText(context,"Unsuccessful", Toast.LENGTH_SHORT).show()

        }


    }


    private fun buildAlertMessageNoGps() {
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(context)
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id -> context?.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
            .setNegativeButton("No", { dialog, id -> dialog.cancel() })
        val alert: android.app.AlertDialog? = builder.create()
        alert!!.show()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_PRESSURE) {
            pressure = event.values[0]
        }

        getCurrentLocation()
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }


}