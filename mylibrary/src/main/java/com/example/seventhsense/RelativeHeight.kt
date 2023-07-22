package com.example.seventhsense

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class RelativeHeight(context: Context, activity: AppCompatActivity){

    var context1: Context = context
    var activity1: Activity = activity

    var latitude: String = ""
    var longitude: String = ""
    var altitude: String = ""
    var accuracy: String = ""
    private var loc: String = ""



    var fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)

    var csv: CSV = CSV(context)

    fun isLocationEnabled(): Boolean {
        val manager: LocationManager = context1?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            buildAlertMessageNoGps()
        }

        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER)||manager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)||manager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)

    }


    @SuppressLint("MissingPermission")
    fun getCurrentAltitudeDetails(){

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
                    Log.i("PressureAlt","Accuracy: ${location.accuracy}")

                    latitude = location.latitude.toString()
                    longitude = location.longitude.toString()
                    altitude = location.altitude.toString()
                    accuracy = location.accuracy.toString()

                    Log.i("AD","$latitude,$longitude,$altitude,$accuracy")
                    csv.record("$latitude,$longitude,$altitude,$accuracy","AltitudeDetails")

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