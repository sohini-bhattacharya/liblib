package com.example.seventhsense

import android.content.Context
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter

class CSVTest(context: Context ) {

    var context1: Context = context
    lateinit var fileOutputStream: FileOutputStream
    lateinit var outputWriter: OutputStreamWriter

    fun record(data: String, name: String) {
        try {
            fileOutputStream =
                context1.applicationContext.openFileOutput("$name.txt", Context.MODE_APPEND)
            outputWriter = OutputStreamWriter(fileOutputStream)
            outputWriter.write(data + "\n")
            outputWriter.close()

        } catch (e: IOException) {
        }
    }
}