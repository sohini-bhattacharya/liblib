//package com.example.mylibrary
//
//import android.annotation.SuppressLint
//import android.app.Activity
//import android.content.Context
//import android.content.Context.LOCATION_SERVICE
//import android.content.Intent
//import android.location.Location
//import android.location.LocationManager
//import android.provider.Settings
//import android.util.Log
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.content.ContextCompat.getSystemService
//import androidx.core.content.ContextCompat.startActivity
//import com.google.android.gms.location.FusedLocationProviderClient
//import com.google.android.gms.location.LocationServices
//import java.lang.Thread.sleep
//
//class LocationTest(context1: Context, activity1: AppCompatActivity) {
//
//    var context: Context = context1
//    var activity: Activity = activity1
//
//    var latitude: String = ""
//    var longitude: String = ""
//    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
//
//    fun start(){
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
//        getCurrentLocation()
//
//
//    }
//
//
//    @SuppressLint("MissingPermission")
//    private fun getCurrentLocation(){
//
//        if(isLocationEnabled()) {
//
//            fusedLocationProviderClient.lastLocation.addOnCompleteListener(this){ task->
//                val location: Location?=task.result
//                if(location==null){
//                    Toast.makeText(context,"Null", Toast.LENGTH_SHORT).show()
////                    locationRequest = LocationRequest.create();
////                    locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
////                    locationRequest.setInterval(20 * 1000);
//                }
//                else{
////                    Toast.makeText(this,"Success LOC", Toast.LENGTH_SHORT).show()
//
////                    latitude = ""+location.latitude
////                    longitude = ""+location.longitude
//
//                    Log.i("TAG","$location")
//                    Log.i("TAG","${location.latitude}")
//                    Log.i("TAG","${location.longitude}")
//                    Log.i("TAG","${location.altitude}")
//                    Log.i("TAG","${location.bearing}")
//
//
//                }
//            }
//        }
//        else{
//            Toast.makeText(context,"Unsucccessful", Toast.LENGTH_SHORT).show()
//
//        }
//
//    }
//
//
//    private fun isLocationEnabled(): Boolean {
//        val manager: LocationManager = context?.getSystemService(LOCATION_SERVICE) as LocationManager
//        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
//        {
//            buildAlertMessageNoGps()
//        }
//
//        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER)||manager.isProviderEnabled(
//            LocationManager.NETWORK_PROVIDER)||manager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)
//
//    }
//
//    private fun buildAlertMessageNoGps() {
//        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(context)
//        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
//            .setCancelable(false)
//            .setPositiveButton("Yes") { dialog, id -> activity?.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
//            .setNegativeButton("No", { dialog, id -> dialog.cancel() })
//        val alert: android.app.AlertDialog? = builder.create()
//        alert!!.show()
//    }
//}