package com.example.cinemaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cinemaapp.utils.CustomAdapterActor
import com.example.cinemaapp.utils.CustomAdapterMovie
import com.example.cinemaapp.utils.api.ApiClient
import com.example.cinemaapp.utils.api.ApiClient.actors
import com.example.cinemaapp.utils.data_model.Actor
import com.example.cinemaapp.utils.data_model.Movie
import kotlinx.android.synthetic.main.activity_actors_view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class ActorsView : AppCompatActivity() {
    lateinit var adapter: CustomAdapterMovie
    var moviesAux = mutableListOf<Movie>()

    val movies by lazy {
        runBlocking{
            initMovies()
        }
    }

    suspend fun initMovies() : MutableList<Movie> {
        var result = mutableListOf<Movie>()
        withContext(Dispatchers.IO) {
            result.addAll(ApiClient.movies)
        }
        return result
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actors_view)

        val id = intent.getStringExtra(ID)
        for (i in movies) {
            var movie = i
            for (j in movie.actors) {
                if (j.toString() == id) {
                    moviesAux.add(movie)
                }
            }
        }

        adapter = CustomAdapterMovie(context = this@ActorsView, resourceId = R.layout.row_element, items = moviesAux)
        movies_list.adapter = this@ActorsView.adapter
    }
}
