package com.example.cinemaapp

import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_movie_list.*
import com.example.cinemaapp.utils.CustomAdapterMovie
import com.example.cinemaapp.utils.api.ApiClient
import com.example.cinemaapp.utils.data_model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MovieList : AppCompatActivity() {
    lateinit var adapter: CustomAdapterMovie

    val movies by lazy {
        runBlocking{
            init()
        }
    }

    suspend fun init() : MutableList<Movie> {
        var result = mutableListOf<Movie>()
        withContext(Dispatchers.IO) {
            result.addAll(ApiClient.movies)
        }
        return result
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        adapter = CustomAdapterMovie(context = this@MovieList, resourceId = R.layout.row_element_movie, items = movies)
        movie_list.adapter = this@MovieList.adapter
        movie_list.setOnItemClickListener { parent, view, position, id ->
            val movie = movies[position]
            Toast.makeText(this@MovieList, movie.toString(), Toast.LENGTH_LONG).show()
            val intent = Intent(this@MovieList, ViewMovie::class.java)
            intent.putExtra("Title", movie.title)
            intent.putExtra("Description", movie.description)
            intent.putExtra("Director", movie.director)
            intent.putExtra("Year", movie.year.toString())
            intent.putExtra("Rating", movie.rating.toString())
            intent.putExtra("Votes", movie.votes.toString())
            intent.putExtra("Revenue", movie.revenue.toString())
            intent.putExtra("Length", movie.length.toString())
            startActivity(intent)
            adapter.getView(position, view, parent)
            Log.d("RESULT", "Data updated")
        }
        movie_list.setOnItemLongClickListener { parent, view, position, id ->
            Toast.makeText(this@MovieList, "Long click", Toast.LENGTH_LONG).show()
            return@setOnItemLongClickListener true
        }

    }

    fun updateUI() {
        val adapter = movie_list.adapter as CustomAdapterMovie
        adapter.notifyDataSetChanged()
        updateUI()
    }
}