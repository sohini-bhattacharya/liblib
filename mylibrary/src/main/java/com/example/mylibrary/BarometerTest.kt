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
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.text.SimpleDateFormat
import java.util.*

class BarometerTest(context1: Context, activity1: AppCompatActivity) : SensorEventListener {

    lateinit var outputWriter: OutputStreamWriter
    lateinit var fileOutputStream: FileOutputStream

    var context: Context = context1

    var light: Float = 0.0f
    var proxi: Float = 0.0f
    var hour: Float = 0.0f

    var final: String = "OUTDOOR"

    private lateinit var light_manager: SensorManager
    private var light_sensor: Sensor? = null

    private lateinit var proxi_manager: SensorManager
    private var proxi_sensor: Sensor? = null

    var tflite: Interpreter? = null
    var prediction: Int = 0

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

    var activity: Activity = activity1

    var fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)

    private lateinit var powerManager: PowerManager
    private lateinit var wakeLock: PowerManager.WakeLock

    private lateinit var pressure_manager: SensorManager
    private var pressure_sensor: Sensor? = null

    var csv: CSVTest = CSVTest(context)

    var status: Boolean = false
    var all: String = "h"


    @SuppressLint("MissingPermission")
    fun getBarometer() {

        Toast.makeText(context, "Sensor Started", Toast.LENGTH_LONG).show();

        powerManager = context?.getSystemService(Service.POWER_SERVICE) as PowerManager

        wakeLock = powerManager.newWakeLock(
            PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK,
            "MyApp::MyWakelockTag"
        )
        wakeLock.acquire()

        pressure_manager = context.getSystemService(AppCompatActivity.SENSOR_SERVICE) as SensorManager
        pressure_sensor = pressure_manager!!.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        light_manager = context?.getSystemService(AppCompatActivity.SENSOR_SERVICE) as SensorManager
        light_sensor = light_manager!!.getDefaultSensor(Sensor.TYPE_LIGHT)

        proxi_manager = context?.getSystemService(AppCompatActivity.SENSOR_SERVICE) as SensorManager
        proxi_sensor = proxi_manager!!.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        light_manager.registerListener(this, light_sensor, SensorManager.SENSOR_DELAY_FASTEST)
        proxi_manager.registerListener(this, proxi_sensor, SensorManager.SENSOR_DELAY_FASTEST)


//        status = true

        getPrediction()

    }

    fun getPrediction(){


        try{
            Log.i("here","howyadoing")
            tflite = Interpreter(loadModel())
        }
        catch (ex: java.lang.Exception){
            ex.printStackTrace()
        }

        status = true
    }

    private fun loadModel(): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd("logistic.tflite")

        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)

        val fileChannel = inputStream.channel

        val start = fileDescriptor.startOffset

        val end = fileDescriptor.declaredLength

        return fileChannel.map(FileChannel.MapMode.READ_ONLY,start,end)
    }

    private fun useModel(h:Float,l:Float,p:Float): Float {
//    val even = arrayOf<Any>()
//    val odd = arrayOf(0.0,1.0,1.0)
//
//    val lala = arrayOf(odd, even)
        val test = arrayOf(floatArrayOf(h, l, p))
        val array = Array(1) { FloatArray(3) {0.0f} }
        val input = FloatArray(1)

//        input[0] = inputX.toFloat()

        Log.i("here","hi")

//    val input = [0,1,1]
//        val input = Array(3) {Array(1) {Array(1) {0}} }
        val output = Array(1){ FloatArray(1) }

        tflite!!.run(test,output)
        Log.i("here","hiHI")

        return output[0][0]
    }

//    fun csv(str: String) {
//        try {
//            fileOutputStream =
//                context.applicationContext.openFileOutput("TEST123.txt", Context.MODE_APPEND)
//            outputWriter = OutputStreamWriter(fileOutputStream)
//            outputWriter.write(str + "\n")
//            outputWriter.close()
//
//        } catch (e: IOException) {
//        }
//    }



    override fun onSensorChanged(event: SensorEvent?) {

        if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
            var l1 = event.values[0]


            if(l1>100){
                light = 1F
            }
            else{
                light = 0F
            }


            Log.v("LIGHT", "${System.currentTimeMillis()},${light}")


        }

        if (event?.sensor?.type == Sensor.TYPE_PROXIMITY) {
            var p1 = event.values[0]

            if(p1>0){
                proxi = 1F
            }
            else{
                proxi = 0F
            }

            Log.v("PROXI", "${System.currentTimeMillis()},${proxi}")


        }
        if (event?.sensor?.type == Sensor.TYPE_PRESSURE) {
            pressure = event.values[0]
        }

        getCurrentLocation()

        if(status) {
            final = "INDOOR"

            Log.i("here","here")
            val prediction = useModel(0.0f,light,proxi)
            Log.i("here","here again")
            if(prediction>0.5f) {

                csv.record(
                    "OUTDOOR,$latitude,$longitude,$bearing,$pressure,$mAlt",
                    "BAROMETER"
                )
            } else{
                csv.record(
                    "INDOOR,$latitude,$longitude,$altitude,$bearing,$pressure",
                    "BAROMETER"
                )
            }
//            csv.record(final,"PREDICT")
            Log.i("here","here again here")

            Log.i("final","${final}, ${prediction}")
            Log.i("TEST","${hour.toInt()},${proxi.toInt()},${light.toInt()}")
//
            Log.i("DOOR","${System.currentTimeMillis()},${
                SimpleDateFormat("HH", Locale.US).format(
                    Date()
                )},${proxi.toInt()},${light.toInt()}")

        }


    }

    fun getStatus(): String {
        return status.toString()
    }

    fun stop(){
        light_manager.unregisterListener(this)
        proxi_manager.unregisterListener(this)
        pressure_manager.unregisterListener(this)

        status = false
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



    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }


}