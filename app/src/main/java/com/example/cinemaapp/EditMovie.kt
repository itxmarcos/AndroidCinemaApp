package com.example.cinemaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import com.example.cinemaapp.utils.CustomAdapterActor
import com.example.cinemaapp.utils.CustomAdapterGenre
import com.example.cinemaapp.utils.CustomAdapterSpinnerActor
import com.example.cinemaapp.utils.CustomAdapterSpinnerGenre
import com.example.cinemaapp.utils.api.ApiClient
import com.example.cinemaapp.utils.data_model.Actor
import com.example.cinemaapp.utils.data_model.Genre
import com.example.cinemaapp.utils.data_model.Movie
import kotlinx.android.synthetic.main.activity_add_movie.*
import kotlinx.android.synthetic.main.activity_edit_movie.*
import kotlinx.android.synthetic.main.activity_view_movie.*
import kotlinx.android.synthetic.main.activity_view_movie.txt_description
import kotlinx.android.synthetic.main.activity_view_movie.txt_director
import kotlinx.android.synthetic.main.activity_view_movie.txt_length
import kotlinx.android.synthetic.main.activity_view_movie.txt_rating
import kotlinx.android.synthetic.main.activity_view_movie.txt_revenue
import kotlinx.android.synthetic.main.activity_view_movie.txt_title
import kotlinx.android.synthetic.main.activity_view_movie.txt_votes
import kotlinx.android.synthetic.main.activity_view_movie.txt_year
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class EditMovie : AppCompatActivity() {
    lateinit var movie : Movie
    lateinit var spinnerActors : Spinner
    lateinit var spinnerGenres : Spinner
    lateinit var adapterSpinnerGenre: CustomAdapterSpinnerGenre
    lateinit var adapterSpinnerActor: CustomAdapterSpinnerActor

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
        setContentView(R.layout.activity_edit_movie)

        val id = intent.getStringExtra(ID)
        movie = ApiClient.movies.first { it.id == id }

        txt_title.setText(movie.title)
        txt_description.setText(movie.description)
        txt_director.setText(movie.director)
        txt_year.setText(movie.year.toString())
        txt_length.setText(movie.runtime.toString())
        txt_rating.setText(movie.rating.toString())
        txt_votes.setText(movie.votes.toString())
        txt_revenue.setText(movie.revenue.toString())

        var myActorId = ""
        spinnerActors = findViewById(R.id.spinner_actors) as Spinner
        adapterSpinnerActor =  CustomAdapterSpinnerActor(context = this, actors = actors.toTypedArray())
        spinnerActors.adapter = adapterSpinnerActor
        spinnerActors.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                myActorId = actors[position].id
            }
        }

        var myGenreId = ""
        spinnerGenres = findViewById(R.id.spinner_genres) as Spinner
        adapterSpinnerGenre =  CustomAdapterSpinnerGenre(context = this, genres = genres.toTypedArray())
        spinnerGenres.adapter = adapterSpinnerGenre
        spinnerGenres.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                myGenreId = genres[position].id
            }
        }

        //Si no me funciona el count, poner un movies.size
        var movieEdited = """{"id": """" + (movies.count()+1).toString() + """","title": """" + txt_title.text + """","description": """" + txt_description.text + """","director": """" + txt_director.text + """","year": """" + txt_year.text + """","runtime": """" + txt_length.text + """","rating": """" + txt_rating.text + """","votes": """" + txt_votes.text + """","revenue": """" + txt_revenue.text + """","genres": ["""" + myGenreId + """"],"actors": ["""" + myActorId + """"]}"""
        Log.e("FILM: ", movieEdited)
        val urlMovie ="$REMOTE/mobile/user/updateMovie.php?user=$USER&pass=$PASS"

        btn_save_changes.setOnClickListener {
            var thread = Thread(kotlinx.coroutines.Runnable {
                post(urlMovie, movieEdited)
            }).start()
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent)
        }

        //Esto se supone que es para cuando cambie el texto que sea consciente de ello
        /*txt_title.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                title = txt_title.text.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

        })*/
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
}
