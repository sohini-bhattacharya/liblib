package com.example.seventhsense

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.content.Context.BLUETOOTH_SERVICE
import android.util.Log
import android.widget.Toast
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.*

class BLETest(context1: Context) {
    var context: Context = context1

    lateinit var fileOutputStream: FileOutputStream
    lateinit var outputWriter: OutputStreamWriter
    var status: Boolean = false

    var csv: CSVTest = CSVTest(context)

    private val bluetoothAdapter: BluetoothAdapter by lazy {
        (context?.getSystemService(BLUETOOTH_SERVICE) as BluetoothManager).adapter
    }

    fun getBLE() {
        startBLEScan()

        Toast.makeText(context, "BLE Started", Toast.LENGTH_LONG).show();

        status = true
    }
    fun stop(){
        stopBLEScan()

        Toast.makeText(context, "BLE Stopped", Toast.LENGTH_LONG).show()

        status = false
    }

    private val bleScanCallback : ScanCallback by lazy {
        object : ScanCallback() {

            @SuppressLint("MissingPermission")
            override fun onScanResult(callbackType: Int, result: ScanResult?) {
                super.onScanResult(callbackType, result)
                Log.v("BACK", "onScanResult")
                showToast("onScanResult")

                val bluetoothDevice = result?.device

                if (bluetoothDevice != null) {

                    val entry = "${bluetoothDevice.name},${bluetoothDevice.address},${bluetoothDevice.uuids}"+ "," + SimpleDateFormat("HH:mm:ss", Locale.US).format(
                        Date()
                    )
                    csv.record("$entry"+","+ SimpleDateFormat("HH:mm:ss", Locale.US).format(Date()),"BLE")
                    Log.i(
                        "BACK","${bluetoothDevice.name} | ${bluetoothDevice.address} | ${bluetoothDevice.uuids}"+ " | " + SimpleDateFormat("HH:mm:ss", Locale.US).format(
                            Date()
                        ))
//                    showToast("${bluetoothDevice.name} | ${bluetoothDevice.address} | ${bluetoothDevice.uuids}"+ " | " +SimpleDateFormat("HH:mm:ss", Locale.US).format(Date()))

//                   Toast.makeText(this,"Device Name ${bluetoothDevice.name} Device Address ${bluetoothDevice.uuids}",Toast.LENGTH_LONG).show()
                }
            }

        }
    }

//
//    fun openBtActivity() {
//        Log.i("TAG","open bt" )
//        val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
//        btRequestActivity.launch(intent)
//    }

    @SuppressLint("MissingPermission")
    fun startBLEScan(){
        Log.v("BACK", "StartBLEScan")

        var scanFilter = ScanFilter.Builder().build()

        var scanFilters : MutableList<ScanFilter> = mutableListOf()
        scanFilters.add(scanFilter)

        var scanSettings = ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).build();

        showToast("Start Scan")

        bluetoothAdapter!!.bluetoothLeScanner.startScan(scanFilters,scanSettings,bleScanCallback)

        Log.v("BACK", "Start Scan2")
    }

    @SuppressLint("MissingPermission")
    fun stopBLEScan(){
        bluetoothAdapter!!.bluetoothLeScanner.stopScan(bleScanCallback)

    }

//    private fun csv(str:String) {
//        try {
//            fileOutputStream = context.applicationContext.openFileOutput("TEST10.txt", Context.MODE_APPEND)
//            outputWriter = OutputStreamWriter(fileOutputStream)
//            outputWriter.write(str+"\n")
//            outputWriter.close()
//
//        } catch (e: IOException) {
//
//        }
//    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG)
            .show()
    }
}