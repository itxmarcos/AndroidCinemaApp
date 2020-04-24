package com.example.cinemaapp.utils

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.cinemaapp.R
import com.google.gson.Gson
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

const val SERVER= "http://10.0.2.2"

class HttpJsonGson : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread(Runnable {
            //user=test&pass=1234 --> created from phpMyAdmin, not valid
            //user=test2&pass=1234 --> created from createUser.php. Returned "User created properly :)".
            //localhost won´t work because the emulator runs on another network, 10.0.2.2. We have to add an extra line in the AndroidManifest.xml to allow HTTP requests: android:usesCleartextTraffic="true"
            var url = URL("$SERVER/mobile/user/getActors.php?user=test2&pass=1234")
            var urlConnection = url.openConnection() as HttpURLConnection
            try {
                var inputStream = BufferedInputStream(urlConnection.getInputStream())
                readStream(inputStream)
                //Print the content
                val jsonString = readStream(inputStream)
                val gson = Gson()
                val actors = gson.fromJson(jsonString, Array<Actor>::class.java)
                Log.e("CONTENT: ", actors.map {
                    it.toString()
                }.toString()).toString()
            } finally {
                urlConnection.disconnect()
            }
        }).start()
    }

    fun readStream(inputStream : InputStream) : String {
        val br = BufferedReader(InputStreamReader(inputStream))
        val total = StringBuilder()
        while (true) {
            val line = br.readLine() ?: break
            total.append(line).append('\n')
        }
        return total.toString()
    }
}

data class Actor(val id: String, val name: String) {
    override fun toString(): String {
        return "$id.- Actor name: $name"
    }
}
