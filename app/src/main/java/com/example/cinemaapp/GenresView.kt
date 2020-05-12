package com.example.cinemaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cinemaapp.utils.CustomAdapterGenre
import com.example.cinemaapp.utils.CustomAdapterMovie
import com.example.cinemaapp.utils.api.ApiClient
import com.example.cinemaapp.utils.data_model.Genre
import com.example.cinemaapp.utils.data_model.Movie
import kotlinx.android.synthetic.main.activity_actors_view.*
import kotlinx.android.synthetic.main.activity_genres_view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class GenresView : AppCompatActivity() {
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
        setContentView(R.layout.activity_genres_view)

        val id = intent.getStringExtra(ID)
        for (i in movies) {
            var movie = i
            for (j in movie.genres) {
                if (j.toString() == id) {
                    moviesAux.add(movie)
                }
            }
        }

        adapter = CustomAdapterMovie(context = this@GenresView, resourceId = R.layout.row_element, items = moviesAux)
        movies_list_genres.adapter = this@GenresView.adapter
    }
}
