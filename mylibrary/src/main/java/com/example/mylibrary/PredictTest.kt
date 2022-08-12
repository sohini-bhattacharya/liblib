package com.example.mylibrary

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.SensorManager.SENSOR_DELAY_FASTEST
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.text.SimpleDateFormat
import java.util.*


class PredictTest(context1: Context): SensorEventListener {

    var context: Context = context1

    var light: Float = 0.0f
    var proxi: Float = 0.0f
    var status: Boolean = false
    var hour: Float = 0.0f

    private lateinit var light_manager: SensorManager
    private var light_sensor: Sensor? = null

    private lateinit var proxi_manager: SensorManager
    private var proxi_sensor: Sensor? = null

    lateinit var fileOutputStream: FileOutputStream
    lateinit var outputWriter: OutputStreamWriter

    var tflite: Interpreter? = null
    var prediction: Int = 0

    fun start(){
        light_manager = context?.getSystemService(AppCompatActivity.SENSOR_SERVICE) as SensorManager
        light_sensor = light_manager!!.getDefaultSensor(Sensor.TYPE_LIGHT)

        proxi_manager = context?.getSystemService(AppCompatActivity.SENSOR_SERVICE) as SensorManager
        proxi_sensor = proxi_manager!!.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        light_manager.registerListener(this, light_sensor, SENSOR_DELAY_FASTEST)
        proxi_manager.registerListener(this, proxi_sensor, SENSOR_DELAY_FASTEST)

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

    fun csv(str: String) {
        try {
            fileOutputStream =
                context.applicationContext.openFileOutput("TEST123.txt", Context.MODE_APPEND)
            outputWriter = OutputStreamWriter(fileOutputStream)
            outputWriter.write(str + "\n")
            outputWriter.close()

        } catch (e: IOException) {
        }
    }



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
//            csvlight("${System.currentTimeMillis()},${light}")


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
//            csvproxi("${System.currentTimeMillis()},${proxi}")


        }
        if(status) {
            var final = "INDOOR"

            Log.i("here","here")
            val prediction = useModel(0.0f,light,proxi)
            Log.i("here","here again")
            final = if(prediction>0.5f) {
                "OUTDOOR"
            } else{
                "INDOOR"
            }
            csv(final)
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

        status = false
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }

}