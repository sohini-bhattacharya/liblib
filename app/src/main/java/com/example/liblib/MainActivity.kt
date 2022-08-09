package com.example.liblib

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import com.example.mylibrary.SensorTest
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity() {


    lateinit var text: TextView
    lateinit var s: SensorTest

    lateinit var button: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button_status)
        text = findViewById(R.id.txt)

        s = SensorTest(this)



        button.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
                ) {
                    s.start()
                    text.text = s.status.toString()


                } else {
//                    requestForUpdates()
                }
            } else {
                s.stop()
                text.text = s.status.toString()
//                deregisterForUpdates()
            }
        }


//        s.stop(this)

    }


}