package com.example.seventhsense

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class Location(context: Context, activity: AppCompatActivity){

    var context1: Context = context
    var activity1: Activity = activity



    var latitude: String = ""
    var longitude: String = ""
    var altitude: String = ""
    var bearing: String = ""
    var accuracy: String = ""
    var speed: String = ""
    var speedAccuracy: String = ""
    var loc: String = ""

    var fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity1)

    var csv: CSV = CSV(context1)

    fun isLocationEnabled(): Boolean {
        val manager: LocationManager = context1?.getSystemService(LOCATION_SERVICE) as LocationManager
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
                    Log.i("LOCATION","${location.accuracy}")
                    Log.i("LOCATION","${location.speed}")
                    Log.i("LOCATION","${location.speedAccuracyMetersPerSecond}")

                    latitude = location.latitude.toString()
                    longitude = location.longitude.toString()
                    altitude = location.altitude.toString()
                    bearing = location.bearing.toString()
                    accuracy = location.accuracy.toString()
                    speed = location.speed.toString()
                    speedAccuracy = location.speedAccuracyMetersPerSecond.toString()

                    Log.i("LOC","$latitude,$longitude,$altitude,$bearing,$accuracy,$speed,$speedAccuracy")
                    csv.record("$latitude,$longitude,$altitude,$bearing,$accuracy,$speed,$speedAccuracy","LOCATION")

                }


            }
        }
        else{
            Toast.makeText(context1,"Unsuccessful", Toast.LENGTH_SHORT).show()

        }


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