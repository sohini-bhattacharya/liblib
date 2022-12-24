package com.example.seventhsense

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.ActivityRecognitionResult
import com.google.android.gms.location.DetectedActivity
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.*

class ActivityRecognitionReceiver : BroadcastReceiver() {

    lateinit var fileOutputStream: FileOutputStream
    lateinit var outputWriter: OutputStreamWriter

    override fun onReceive(context: Context, intent: Intent) {
        Log.i("NEW","Working")

        Toast.makeText(context,"SUCCESS",Toast.LENGTH_LONG).show()

        if(ActivityRecognitionResult.hasResult(intent)) {

            val result = ActivityRecognitionResult.extractResult(intent)

            Log.i("result","$result")

            result?.let {
                result.probableActivities.forEach { event ->

//                    type = event.type
//                    conf = event.confidence
                    val info = toActivityString(event.type) +
                            "," + event.confidence + "," +
                            SimpleDateFormat("HH:mm:ss", Locale.US).format(Date())

//                    Log.i("NEW","$info")

                    if (event.confidence > 30 && toActivityString(event.type) != "UNKNOWN") {

                        try {
                            fileOutputStream = context.applicationContext.openFileOutput("TEST123.txt", Context.MODE_APPEND)
                            outputWriter = OutputStreamWriter(fileOutputStream)
                            outputWriter.write(info+"\n")
                            outputWriter.close()

                        } catch (e: IOException) {
                        }
                        Log.i("CHECK", "${info}")
                        Toast.makeText(context, "${info}", Toast.LENGTH_LONG).show()

                    }
//                    Notify
//                        .with(context)
//                        .content {
//                            title = "Activity Detected"
//                            text =
//                                "Transition: " + toActivityString(event.type) +
//                                        " " + event.confidence + " " +
//                                        SimpleDateFormat("HH:mm:ss", Locale.US).format(Date())
//
//                        }
//                        .show(id = Constants.ACTIVITY_TRANSITION_NOTIFICATION_ID)
//
//                    Toast.makeText(context, info, Toast.LENGTH_LONG).show()
                }



            }
//            sleep(5000)
        }
        else{

            Log.i("NEW1","NO RESULTS")
        }
    }
//    private fun csv(str:String) {
//        try {
//            fileOutputStream = context.applicationContext.openFileOutput("NEWFILENEW.txt", Context.MODE_APPEND)
//            outputWriter = OutputStreamWriter(fileOutputStream)
//            outputWriter.write(str+"\n")
////                startFlag = true
//            outputWriter.close()
//
//        } catch (e: IOException) {
//        }
//    }


    private fun toActivityString(activity: Int): String {
        return when (activity) {
            DetectedActivity.STILL -> "STILL"
            DetectedActivity.WALKING -> "WALKING"
            DetectedActivity.IN_VEHICLE -> "IN VEHICLE"
            DetectedActivity.RUNNING -> "RUNNING"
            DetectedActivity.ON_FOOT -> "ON FOOT"
            DetectedActivity.ON_BICYCLE -> "ON BICYCLE"
            DetectedActivity.TILTING -> "TILTING"

            else -> "UNKNOWN"
        }
    }

}