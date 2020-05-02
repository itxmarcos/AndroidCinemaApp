package com.example.cinemaapp

import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_movie_list.*
import com.example.cinemaapp.utils.CustomAdapterMovies
import com.example.cinemaapp.utils.api.ApiClient
import com.example.cinemaapp.utils.data_model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

const val ID = "ID"
const val EDIT_CODE = 27

class MovieList : AppCompatActivity() {
    lateinit var adapter: CustomAdapterMovies

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

        adapter = CustomAdapterMovies(context = this@MovieList, resourceId = R.layout.row_element_movie, items = movies)
        movie_list.adapter = this@MovieList.adapter
        movie_list.setOnItemClickListener { _, _, _, _ ->
            Toast.makeText(this@MovieList, "Short click", Toast.LENGTH_LONG).show()
        }
        movie_list.setOnItemLongClickListener { parent, view, position, id ->
            Toast.makeText(this@MovieList, "Long click", Toast.LENGTH_LONG).show()
            return@setOnItemLongClickListener true
        }

    }


    fun updateUI() {
        val adapter = movie_list.adapter as CustomAdapterMovies
        adapter.notifyDataSetChanged()
        updateUI()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_CODE)
            adapter.notifyDataSetChanged()
    }
}