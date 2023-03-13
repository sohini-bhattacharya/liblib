package com.example.seventhsense

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class PressureAltTest (context: Context, activity: AppCompatActivity): SensorEventListener {

    var context1: Context = context
    var activity1: Activity = activity

    var pressure = 0.0f

    var latitude: String = ""
    var longitude: String = ""
    var altitude: String = ""
    var bearing: String = ""
    var accuracy: String = ""
    var speed: String = ""
    var loc: String = ""


    private lateinit var pressure_manager: SensorManager
    private var pressure_sensor: Sensor? = null

    var fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)

    var csv: CSVTest = CSVTest(context)

    fun initialize(){
        pressure_manager = context1?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        pressure_sensor = pressure_manager!!.getDefaultSensor(Sensor.TYPE_PRESSURE)

    }
    fun isLocationEnabled(): Boolean {
        val manager: LocationManager = context1?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            buildAlertMessageNoGps()
        }

        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER)||manager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)||manager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)

    }

    fun stop(){

        pressure_manager.unregisterListener(this)

        Toast.makeText(context1, "Sensor Stopped", Toast.LENGTH_LONG).show()
        Log.i("HERE", "STOPPED")
    }


    @SuppressLint("MissingPermission")
    fun getCurrentLocation(){

        initialize()

        if(isLocationEnabled()) {

            fusedLocationProviderClient.lastLocation.addOnCompleteListener { task->
                val location: Location?=task.result
                if(location==null){
//                    Toast.makeText(context,"Null", Toast.LENGTH_SHORT).show()
                }
                else{
//                    Toast.makeText(context,"Success LOC", Toast.LENGTH_SHORT).show()

                    loc = location.toString()
                    Log.i("PressureAlt","$location")
                    Log.i("PressureAlt","Latitude: ${location.latitude}")
                    Log.i("PressureAlt","Longitude: ${location.longitude}")
                    Log.i("PressureAlt","Altitude: ${location.altitude}")
                    Log.i("PressureAlt","Bearing: ${location.bearing}")
                    Log.i("PressureAlt","Accuracy: ${location.accuracy}")
                    Log.i("PressureAlt","Speed: ${location.speed}")
                    Log.i("PressureAlt","Pressure: $pressure")

                    latitude = location.latitude.toString()
                    longitude = location.longitude.toString()
                    altitude = location.altitude.toString()
                    bearing = location.bearing.toString()
                    accuracy = location.accuracy.toString()
                    speed = location.speed.toString()

                    Log.i("PA","$pressure,$latitude,$longitude,$altitude,$bearing,$accuracy,$speed")
                    csv.record("$pressure,$latitude,$longitude,$altitude,$bearing,$accuracy,$speed","PressureAlt")

                }


            }
        }
        else{
            Toast.makeText(context1,"Unsuccessful", Toast.LENGTH_SHORT).show()

        }


    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_PRESSURE) {
            pressure = event.values[0]
            csv.record("$pressure,$latitude,$longitude,$altitude,$bearing,$accuracy,$speed","PressureAltAll")

        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }

    private fun buildAlertMessageNoGps() {
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(context1)
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id -> context1?.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
            .setNegativeButton("No", { dialog, id -> dialog.cancel() })
        val alert: android.app.AlertDialog? = builder.create()
        alert!!.show()
    }

}