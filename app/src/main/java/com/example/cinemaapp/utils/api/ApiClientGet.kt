package com.example.cinemaapp.utils.api

import com.example.cinemaapp.utils.data_model.Actor
import com.example.cinemaapp.utils.data_model.Genre
import com.example.cinemaapp.utils.data_model.Movie
import com.google.gson.Gson
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

const val REMOTE = "https://movies-api-v2.000webhostapp.com"
const val USER = "mobile"
const val PASS = "apps"

/**
 * Objeto singleton que nos permite acceder a los elementos almacenados en él desde cualquier parte de
 * nuestra aplicación.
 */
object ApiClient {

    val movies : MutableList<Movie> by lazy {
        loadMovies()
    }
    val genres : MutableList<Genre> by lazy {
        loadGenres()
    }
    val actors : MutableList<Actor> by lazy {
        loadActors()
    }

    fun loadMovies() : MutableList<Movie> {
        var urlMovie = URL("$REMOTE/mobile/user/getMovies.php?user=$USER&pass=$PASS")
        var urlConnectionMovie = urlMovie.openConnection() as HttpURLConnection
        val inputStreamMovie = BufferedInputStream(urlConnectionMovie.inputStream)

        val gson = Gson()
        val jsonStringMovies = readStream(inputStreamMovie)
        val array = gson.fromJson(jsonStringMovies, Array<Movie>::class.java)
        urlConnectionMovie.disconnect()
        val mutableList = mutableListOf<Movie>()
        mutableList.addAll(array)
        return mutableList
    }

    fun loadGenres() : MutableList<Genre> {
        var urlGenre = URL("$REMOTE/mobile/user/getGenres.php?user=$USER&pass=$PASS")
        var urlConnectionGenre = urlGenre.openConnection() as HttpURLConnection
        val inputStreamGenre = BufferedInputStream(urlConnectionGenre.inputStream)

        val gson = Gson()
        val jsonStringGenres = readStream(inputStreamGenre)
        val array = gson.fromJson(jsonStringGenres, Array<Genre>::class.java)
        urlConnectionGenre.disconnect()
        val mutableList = mutableListOf<Genre>()
        mutableList.addAll(array)
        return mutableList
    }

    fun loadActors() : MutableList<Actor> {
        var urlActor = URL("$REMOTE/mobile/user/getActors.php?user=$USER&pass=$PASS")
        var urlConnectionActor = urlActor.openConnection() as HttpURLConnection
        val inputStreamActor = BufferedInputStream(urlConnectionActor.inputStream)

        val gson = Gson()
        val jsonStringActors = readStream(inputStreamActor)
        val array = gson.fromJson(jsonStringActors, Array<Actor>::class.java)
        urlConnectionActor.disconnect()
        val mutableList = mutableListOf<Actor>()
        mutableList.addAll(array)
        return mutableList
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