package com.example.mylibrary

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class PermissionTest(context1: Context, activity1: AppCompatActivity) {
    lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    var ForegroundPermission = false
    var WakePermission = false
    var WritePermission = false
    var ReadPermission = false
    var Internet = false
    var FineLocation = false
    var CoarseLocation = false
    var BLEScan = false
    var BLEAdvert = false
    var BLEConnect = false
    var BLEAdmin = false
    var BLE = false
    var StatePermission = false

    var context: Context = context1

    var activity: AppCompatActivity = activity1

    fun getPermissions(){
        permissionLauncher =
            activity?.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                StatePermission = permissions[Manifest.permission.READ_PHONE_STATE] ?: StatePermission
                BLE = permissions[Manifest.permission.BLUETOOTH] ?: BLE
                BLEScan = permissions[Manifest.permission.BLUETOOTH_SCAN] ?: BLEScan
                BLEAdvert = permissions[Manifest.permission.BLUETOOTH_ADVERTISE] ?: BLEAdvert
                BLEConnect = permissions[Manifest.permission.BLUETOOTH_CONNECT] ?: BLEConnect
                BLEAdmin = permissions[Manifest.permission.BLUETOOTH_ADMIN] ?: BLEAdmin
                CoarseLocation = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: CoarseLocation
                FineLocation = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: FineLocation
                Internet = permissions[Manifest.permission.INTERNET] ?: Internet
                ForegroundPermission = permissions[Manifest.permission.FOREGROUND_SERVICE] ?: ForegroundPermission
                WakePermission = permissions[Manifest.permission.WAKE_LOCK] ?: WakePermission
                WritePermission = permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: WritePermission
                ReadPermission = permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: ReadPermission
            }!!
        requestPermission()
    }

    private fun requestPermission(){

        StatePermission = ContextCompat.checkSelfPermission(context,
            Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED

        BLE = ContextCompat.checkSelfPermission(context,
            Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED

        BLEScan = ContextCompat.checkSelfPermission(context,
            Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED

        BLEAdvert = ContextCompat.checkSelfPermission(context,
            Manifest.permission.BLUETOOTH_ADVERTISE) == PackageManager.PERMISSION_GRANTED

        BLEConnect = ContextCompat.checkSelfPermission(context,
            Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED

        BLEAdmin = ContextCompat.checkSelfPermission(context,
            Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED

        ForegroundPermission = ContextCompat.checkSelfPermission(context,
            Manifest.permission.FOREGROUND_SERVICE) == PackageManager.PERMISSION_GRANTED

        WakePermission = ContextCompat.checkSelfPermission(context,
            Manifest.permission.WAKE_LOCK) == PackageManager.PERMISSION_GRANTED

        FineLocation = ContextCompat.checkSelfPermission(context,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

        Internet = ContextCompat.checkSelfPermission(context,
            Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED

        WritePermission = ContextCompat.checkSelfPermission(context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

        ReadPermission = ContextCompat.checkSelfPermission(context,
            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

        CoarseLocation = ContextCompat.checkSelfPermission(context,
            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        val permissionsRequest : MutableList<String> = ArrayList()

        if(!StatePermission){
            permissionsRequest.add(Manifest.permission.READ_PHONE_STATE)
        }

        if(!BLE){
            permissionsRequest.add(Manifest.permission.BLUETOOTH)
        }

        if(!BLEScan){
            permissionsRequest.add(Manifest.permission.BLUETOOTH_SCAN)
        }

        if(!BLEAdvert){
            permissionsRequest.add(Manifest.permission.BLUETOOTH_ADVERTISE)
        }

        if(!BLEConnect){
            permissionsRequest.add(Manifest.permission.BLUETOOTH_CONNECT)
        }

        if(!BLEAdmin){
            permissionsRequest.add(Manifest.permission.BLUETOOTH_ADMIN)
        }

        if(!CoarseLocation){
            permissionsRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        if(!ForegroundPermission){
            permissionsRequest.add(Manifest.permission.FOREGROUND_SERVICE)
        }

        if(!WakePermission){
            permissionsRequest.add(Manifest.permission.WAKE_LOCK)
        }

        if(!WritePermission){
            permissionsRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if(!ReadPermission){
            permissionsRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if(!Internet){
            permissionsRequest.add(Manifest.permission.INTERNET)
        }

        if(!FineLocation){
            permissionsRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        if(permissionsRequest.isNotEmpty()){
            permissionLauncher.launch(permissionsRequest.toTypedArray())
        }
    }

}