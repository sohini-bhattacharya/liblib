package com.example.mylibrary

import android.content.Context
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter

class CSVTest(context1: Context ) {

    var context: Context = context1
    lateinit var fileOutputStream: FileOutputStream
    lateinit var outputWriter: OutputStreamWriter

    fun csv(data: String, name: String) {
        try {
            fileOutputStream =
                context.applicationContext.openFileOutput("$name.txt", Context.MODE_APPEND)
            outputWriter = OutputStreamWriter(fileOutputStream)
            outputWriter.write(data + "\n")
            outputWriter.close()

        } catch (e: IOException) {
        }
    }
}