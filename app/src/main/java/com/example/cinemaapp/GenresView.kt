package com.example.cinemaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cinemaapp.utils.CustomAdapterGenre
import com.example.cinemaapp.utils.api.ApiClient
import com.example.cinemaapp.utils.data_model.Genre
import com.example.cinemaapp.utils.data_model.Movie
import kotlinx.android.synthetic.main.activity_genres_view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class GenresView : AppCompatActivity() {
    lateinit var adapter: CustomAdapterGenre

    val movies by lazy {
        runBlocking{
            initMovies()
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

    suspend fun initGenres() : MutableList<Genre> {
        var myGenres = mutableListOf<Genre>()
        withContext(Dispatchers.IO) {
            myGenres.addAll(ApiClient.genres)
        }
        return myGenres
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genres_view)

        adapter = CustomAdapterGenre(context = this@GenresView, resourceId = R.layout.row_element, items = genres)
        genres_list.adapter = this@GenresView.adapter

        //Faltaría ver a qué películas pertenece cada género

    }
}
