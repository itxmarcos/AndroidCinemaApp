package com.example.cinemaapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.cinemaapp.utils.CustomAdapterActor
import com.example.cinemaapp.utils.CustomAdapterGenre
import com.example.cinemaapp.utils.CustomAdapterSpinnerGenre
import com.example.cinemaapp.utils.api.ApiClient
import com.example.cinemaapp.utils.data_model.Actor
import com.example.cinemaapp.utils.data_model.Genre
import com.example.cinemaapp.utils.data_model.Movie
import kotlinx.android.synthetic.main.activity_add_movie.*
import kotlinx.android.synthetic.main.row_element.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.android.synthetic.main.row_element.view.*
import kotlinx.android.synthetic.main.row_element.view.textView

const val REMOTE = "https://movies-api-v2.000webhostapp.com"
const val USER = "mobile"
const val PASS = "apps"

class AddMovie : AppCompatActivity() {
    lateinit var spinnerActors : Spinner
    lateinit var spinnerGenres : Spinner
    lateinit var adapterSpinnerGenre: CustomAdapterSpinnerGenre
    lateinit var adapterActor: CustomAdapterActor

    val movies by lazy {
        runBlocking{
            initMovies()
        }
    }

    val actors by lazy {
        runBlocking{
            initActors()
        }
    }

    val genres by lazy {
        runBlocking{
            initGenres()
        }
    }

    suspend fun initMovies() : MutableList<Movie> {
        var result = mutableListOf<Movie>()
        withContext(Dispatchers.IO) {
            result.addAll(ApiClient.movies)
        }
        return result
    }

    suspend fun initActors() : MutableList<Actor> {
        var myActors = mutableListOf<Actor>()
        withContext(Dispatchers.IO) {
            myActors.addAll(ApiClient.actors)
        }
        return myActors
    }

    suspend fun initGenres() : MutableList<Genre> {
        var myGenres = mutableListOf<Genre>()
        withContext(Dispatchers.IO) {
            myGenres.addAll(ApiClient.genres)
        }
        return myGenres
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)

        var myActorId = ""
        spinnerActors = findViewById(R.id.spinner_actors) as Spinner
        adapterActor =  CustomAdapterActor(context = this@AddMovie, resourceId = R.layout.row_element, items = actors)
        spinnerActors.adapter = adapterActor
        spinnerActors.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //adapterActor.getView(position, view, parent)
                myActorId = actors[position].id
            }
        }

        var myGenreId = ""
        spinnerGenres = findViewById(R.id.spinner_genres) as Spinner
        adapterSpinnerGenre =  CustomAdapterSpinnerGenre(context = this@AddMovie, genres = genres.toTypedArray())
        spinnerGenres.adapter = adapterSpinnerGenre
        spinnerGenres.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //adapterSpinnerGenre.getView(position, view, parent)
                myGenreId = genres[position].id
            }
        }

        var movieEdited = """{"id": """" + (movies.count()+1).toString() + """","title": """" + txt_title.text + """","description": """" + txt_description.text + """","director": """" + txt_director.text + """","year": """" + txt_year.text + """","runtime": """" + txt_length.text + """","rating": """" + txt_rating.text + """","votes": """" + txt_votes.text + """","revenue": """" + txt_revenue.text + """","genres": ["""" + myGenreId + """"],"actors": ["""" + myActorId + """"]}"""
        val urlMovie ="$REMOTE/mobile/user/getMovies.php?user=$USER&pass=$PASS"

        btn_add_movie.setOnClickListener {
            post(urlMovie, movieEdited)
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent)
        }
    }

    fun post(url: String, body: String): String {
        return URL(url)
            .openConnection()
            .let {
                it as HttpURLConnection
            }.apply {
                setRequestProperty("Content-Type", "application/json; charset=utf-8")
                requestMethod = "POST"
                doOutput = true
                val outputWriter = OutputStreamWriter(outputStream)
                outputWriter.write(body)
                outputWriter.flush()
            }.let {
                if (it.responseCode == 200) it.inputStream else it.errorStream
            }.let { streamToRead ->
                BufferedReader(InputStreamReader(streamToRead)).use {
                    val response = StringBuffer()
                    var inputLine = it.readLine()
                    while (inputLine != null) {
                        response.append(inputLine)
                        inputLine = it.readLine()
                    }
                    it.close()
                    response.toString()
                }
            }
    }

    /*fun createJsonMovie(movie: Movie): String{
    var json = JSONObject()
    json.put("movie", addMovie(movie))
    return json.toString()
    /*val actors = listOf<Actor>(
        Actor("355", "Gerard Butler"),
        Actor("201", "Aaron Eckhart"),
        Actor("741", "Morgan Freeman"),
        Actor("1018", "Angela Bassett")
    )
    json.put("actors", addActors(actors))*/

    /*val genres = listOf<Genre>(
        Genre("1", "Action"),
        Genre("16", "Crime"),
        Genre("11", "Drama")
    )*/

    //saveJson(json.toString())
    }

    private fun addMovie(movie: Movie): JSONObject {
        return JSONObject()
            .put("id", movie.id)
            .put("title", movie.title)
            .put("description", movie.description)
            .put("director", movie.director)
            .put("year", movie.year)
            .put("runtime", movie.runtime)
            .put("rating", movie.rating)
            .put("votes", movie.votes)
            .put("revenue", movie.revenue)
            .put("genres", movie.genres)
            .put("actors", movie.actors)
    }

    private fun addActors(actors: List<Actor>): JSONArray {
        var actorsJson = JSONArray()
        actors.forEach {
            actorsJson.put(
                JSONArray()
                    .put(it.id)
                    .put(it.name)
            )
        }
        return actorsJson
    }*/

    /*private fun saveJson(jsonString: String) {
        val output: Writer
        var file = createFile()
        output = BufferedWriter(FileWriter(file))
        output.write(jsonString)
        output.close()
    }

    private fun createFile(): File {
        val fileName = "myJson"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        if(!storageDir!!.exists()) {
            storageDir.mkdir()
        }
        return File.createTempFile(
            fileName,
            ".json",
            storageDir
        )
    }*/
}
