package com.example.cinemaapp

import android.content.Context
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_movie_list.*
import com.example.cinemaapp.utils.CustomAdapterMovie
import com.example.cinemaapp.utils.api.ApiClient
import com.example.cinemaapp.utils.data_model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

const val EDIT_CODE = 27
const val ID = "ID"

class MovieList : AppCompatActivity() {
    lateinit var adapter: CustomAdapterMovie

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
        setContentView(R.layout.activity_movie_list)

        adapter = CustomAdapterMovie(context = this@MovieList, resourceId = R.layout.row_element, items = movies)
        movie_list.adapter = this@MovieList.adapter
        movie_list.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, ViewMovie::class.java)
            val movie = movies[position]
            intent.putExtra(ID, movie.id)
            startActivity(intent)
            adapter.getView(position, view, parent)
        }

        movie_list.setOnItemLongClickListener { parent, view, position, id ->
            Toast.makeText(this@MovieList, "Long click", Toast.LENGTH_LONG).show()
            val intent = Intent(this@MovieList, EditMovie::class.java)
            startActivityForResult(intent, EDIT_CODE)
            adapter.getView(position, view, parent)
            return@setOnItemLongClickListener true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_CODE)
            adapter.notifyDataSetChanged()
    }

    /*fun updateUI() {
        val adapter = movie_list.adapter as CustomAdapterMovie
        adapter.notifyDataSetChanged()
        updateUI()
    }*/
}