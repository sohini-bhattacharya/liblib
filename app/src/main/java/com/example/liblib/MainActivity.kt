package com.example.liblib

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.TextView
import com.example.seventhsense.*


class MainActivity : AppCompatActivity() {


    lateinit var text: TextView
//    lateinit var s: SensorTest
//    lateinit var b: BLETest
    lateinit var a: ActivityTest
//    lateinit var p: PredictTest
    lateinit var pe: PermissionTest
//    lateinit var w: WifiTest

    lateinit var button: Switch
//
//    lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
//    var ForegroundPermission = false
//    var ActivityPermission = false
//    var WakePermission = false
//    var WritePermission = false
//    var ReadPermission = false
//    var Internet = false
//    var FineLocation = false
//    var CoarseLocation = false
//    var BLEScan = false
//    var BLEAdvert = false
//    var BLEConnect = false
//    var BLEAdmin = false
//    var BLE = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button_status)
        text = findViewById(R.id.txt)

//
//        permissionLauncher =
//            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
//                BLE = permissions[Manifest.permission.BLUETOOTH] ?: BLE
//                BLEScan = permissions[Manifest.permission.BLUETOOTH_SCAN] ?: BLEScan
//                BLEAdvert = permissions[Manifest.permission.BLUETOOTH_ADVERTISE] ?: BLEAdvert
//                BLEConnect = permissions[Manifest.permission.BLUETOOTH_CONNECT] ?: BLEConnect
//                BLEAdmin = permissions[Manifest.permission.BLUETOOTH_ADMIN] ?: BLEAdmin
//                CoarseLocation = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: CoarseLocation
//                FineLocation = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: FineLocation
//                Internet = permissions[Manifest.permission.INTERNET] ?: Internet
//                ForegroundPermission = permissions[Manifest.permission.FOREGROUND_SERVICE] ?: ForegroundPermission
//                WakePermission = permissions[Manifest.permission.WAKE_LOCK] ?: WakePermission
//                WritePermission = permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: WritePermission
//                ReadPermission = permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: ReadPermission
//                ActivityPermission = permissions[Manifest.permission.ACTIVITY_RECOGNITION] ?: ActivityPermission
//            }

//        s = SensorTest(this)
//
//        b = BLETest(this)

//        a = ActivityTest(this)

//        p = PredictTest(this)


        pe = PermissionTest(applicationContext,this)

        a = ActivityTest(this)

//        requestPermission()

        pe.getPermissions()

        button.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
                ) {
                    a.start()
//                    s.start()
//                    b.start()
//                    w.start()
                    text.text = a.getStatus()


                } else {
//                    requestForUpdates()
                }
            } else {
//                s.stop()
//                b.stop()

                a.stop()
//                w.stop()
                text.text = a.getStatus()
//                deregisterForUpdates()
            }
        }


//        s.stop(this)

    }

//
//    private fun requestPermission(){
//
//
//        BLE = ContextCompat.checkSelfPermission(this,
//            Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED
//
//        BLEScan = ContextCompat.checkSelfPermission(this,
//            Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED
//
//        BLEAdvert = ContextCompat.checkSelfPermission(this,
//            Manifest.permission.BLUETOOTH_ADVERTISE) == PackageManager.PERMISSION_GRANTED
//
//        BLEConnect = ContextCompat.checkSelfPermission(this,
//            Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED
//
//        BLEAdmin = ContextCompat.checkSelfPermission(this,
//            Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED
//
//        ForegroundPermission = ContextCompat.checkSelfPermission(this,
//            Manifest.permission.FOREGROUND_SERVICE) == PackageManager.PERMISSION_GRANTED
//
//        WakePermission = ContextCompat.checkSelfPermission(this,
//            Manifest.permission.WAKE_LOCK) == PackageManager.PERMISSION_GRANTED
//
//        FineLocation = ContextCompat.checkSelfPermission(this,
//            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
//
//        Internet = ContextCompat.checkSelfPermission(this,
//            Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
//
//        WritePermission = ContextCompat.checkSelfPermission(this,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
//
//        ReadPermission = ContextCompat.checkSelfPermission(this,
//            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
//
//        CoarseLocation = ContextCompat.checkSelfPermission(this,
//            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
//
//        ActivityPermission = ContextCompat.checkSelfPermission(this,
//            Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED
//
//        val permissionsRequest : MutableList<String> = ArrayList()
//
//        if(!BLE){
//            permissionsRequest.add(Manifest.permission.BLUETOOTH)
//        }
//
//        if(!BLEScan){
//            permissionsRequest.add(Manifest.permission.BLUETOOTH_SCAN)
//        }
//
//        if(!BLEAdvert){
//            permissionsRequest.add(Manifest.permission.BLUETOOTH_ADVERTISE)
//        }
//
//        if(!BLEConnect){
//            permissionsRequest.add(Manifest.permission.BLUETOOTH_CONNECT)
//        }
//
//        if(!BLEAdmin){
//            permissionsRequest.add(Manifest.permission.BLUETOOTH_ADMIN)
//        }
//
//        if(!CoarseLocation){
//            permissionsRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
//        }
//
//        if(!ForegroundPermission){
//            permissionsRequest.add(Manifest.permission.FOREGROUND_SERVICE)
//        }
//
//        if(!WakePermission){
//            permissionsRequest.add(Manifest.permission.WAKE_LOCK)
//        }
//
//        if(!WritePermission){
//            permissionsRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//        }
//
//        if(!ReadPermission){
//            permissionsRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
//        }
//        if(!Internet){
//            permissionsRequest.add(Manifest.permission.INTERNET)
//        }
//
//        if(!FineLocation){
//            permissionsRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
//        }
//
//        if(!ActivityPermission){
//            permissionsRequest.add(Manifest.permission.ACTIVITY_RECOGNITION)
//    }
//
//        if(permissionsRequest.isNotEmpty()){
//            permissionLauncher.launch(permissionsRequest.toTypedArray())
//        }
//    }
//
//

}