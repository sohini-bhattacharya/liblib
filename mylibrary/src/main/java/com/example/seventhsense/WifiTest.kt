package com.example.seventhsense

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.util.Log
import android.widget.Toast
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class WifiTest(context: Context) {

    var context1: Context = context
    lateinit var wifiManager: WifiManager

    lateinit var fileOutputStream: FileOutputStream
    lateinit var outputWriter: OutputStreamWriter
    private lateinit var wifiLock: WifiManager.WifiLock

    var csv: CSVTest = CSVTest(context1)

    var status: Boolean = false

    fun getWifi(){
        wifiManager = context1?.getSystemService(Service.WIFI_SERVICE) as WifiManager
        wifiLock = wifiManager.createWifiLock(WifiManager.WIFI_MODE_FULL, "myId")
        wifiLock.acquire()
        startScanning()

        status = true

    }

    private fun showToast(message: String) {
        Toast.makeText(context1, message, Toast.LENGTH_LONG)
            .show()
    }

    val wifiScanReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {

            val success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
            if (success) {
                showToast("SUCCESS")
                Log.i("SEE","success")
                scanSuccess()

            }
            else {
                showToast("FAILURE")
                Log.i("SEE","failure")
                scanFailure()
            }
        }
    }
    private fun scanSuccess() {
        var resultList = wifiManager.scanResults as ArrayList<ScanResult>
        for(result in resultList){
            var text = "${result.SSID} + ${result.BSSID} + ${result.capabilities} + ${result.timestamp}"

            csv.record("${result.SSID},${result.BSSID},${result.capabilities}"+","+ SimpleDateFormat("HH:mm:ss", Locale.US).format(
                Date()
            ),"WIFI")
            Log.i("BACK","${result.SSID},${result.BSSID},${result.capabilities}")}
        Thread.sleep(10000)
        wifiManager.startScan()

    }


    private fun scanFailure() {
        @Suppress("DEPRECATION")
        val results = wifiManager.scanResults
//        Log.i("TAG","$results")
        Thread.sleep(10000)
        wifiManager.startScan()

    }

    private fun startScanning() {
        context1?.registerReceiver(wifiScanReceiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
        @Suppress("DEPRECATION")
        wifiManager.startScan()

    }


    fun getStatus(): String {
        return status.toString()
    }

    fun stop() {
        context1?.unregisterReceiver(wifiScanReceiver)
        wifiLock.release()
        status = false
        Toast.makeText(context1, "Service Stopped", Toast.LENGTH_LONG).show()
        Log.i("HERE","STOPPED")
    }
}