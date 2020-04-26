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

class HttpJsonGson: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread(Runnable {
            //user=test&pass=1234 --> created from phpMyAdmin, not valid
            //user=test2&pass=1234 --> created from createUser.php. Returned "User created properly :)".
            //localhost won´t work because the emulator runs on another network, 10.0.2.2. We have to add an extra line in the AndroidManifest.xml to allow HTTP requests: android:usesCleartextTraffic="true"
            var urlActor = URL("$SERVER/mobile/user/getActors.php?user=test2&pass=1234")
            var urlMovie = URL("$SERVER/mobile/user/getMovies.php?user=test2&pass=1234")
            var urlGenre = URL("$SERVER/mobile/user/getGenres.php?user=test2&pass=1234")

            var urlConnectionActor = urlActor.openConnection() as HttpURLConnection
            var urlConnectionMovie = urlMovie.openConnection() as HttpURLConnection
            var urlConnectionGenre = urlGenre.openConnection() as HttpURLConnection

            try {
                val inputStreamActor = BufferedInputStream(urlConnectionActor.getInputStream())
                val inputStreamMovie = BufferedInputStream(urlConnectionMovie.getInputStream())
                val inputStreamGenre = BufferedInputStream(urlConnectionGenre.getInputStream())

                //Print the content
                val jsonStringActors = readStream(inputStreamActor)
                val jsonStringMovies = readStream(inputStreamMovie)
                val jsonStringGenres = readStream(inputStreamGenre)


                val gson = Gson()

                val actors = gson.fromJson(jsonStringActors, Array<Actor>::class.java)
                Log.e("CONTENT: ", actors.map{
                    it.toString()
                }.toString()).toString()

                val movies = gson.fromJson(jsonStringMovies, Array<Movie>::class.java)
                Log.e("CONTENT: ", movies.map{
                    it.toString()
                }.toString()).toString()

                val genres = gson.fromJson(jsonStringGenres, Array<Genre>::class.java)
                Log.e("CONTENT: ", genres.map{
                    it.toString()
                }.toString()).toString()

            }
            finally {
                urlConnectionActor.disconnect()
                urlConnectionMovie.disconnect()
                urlConnectionGenre.disconnect()

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



data class Movie(val id: Int, val tittle: String, val genres: List<Int>, val description: String, val director: String, val actors: List<Int>, val year: Int, val length: Int, val rating: Float, val votes: Int, val revenue: Float) {
    override fun toString(): String {
        return "$id.- Movie tittle: $tittle"
    }
}

data class Genre(val id: Int, val name: String) {
    override fun toString(): String {
        return "$id.- Genre: $name"
    }
}