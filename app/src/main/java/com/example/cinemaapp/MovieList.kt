package com.example.cinemaapp

import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_movie_list.*
import com.example.cinemaapp.utils.CustomAdapterMovie
import com.example.cinemaapp.utils.api.ApiClient
import com.example.cinemaapp.utils.data_model.Actor
import com.example.cinemaapp.utils.data_model.Genre
import com.example.cinemaapp.utils.data_model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

//const val ID = "ID"
const val EDIT_CODE = 27

class MovieList : AppCompatActivity() {
    lateinit var adapter: CustomAdapterMovie

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
        setContentView(R.layout.activity_movie_list)

        adapter = CustomAdapterMovie(context = this@MovieList, resourceId = R.layout.row_element, items = movies)
        movie_list.adapter = this@MovieList.adapter
        movie_list.setOnItemClickListener { parent, view, position, id ->
            val movie = movies[position]

            Log.e("CONTENT: ", actors[0].name.map{
                it.toString()
            }.toString())

            val actorsAux = mutableListOf<String>()
            for (i in movie.actors) {
                val actor = actors.filter {
                    it.id==i
                }
                actorsAux.add(actors[i].name)
            }

            val genresAux = mutableListOf<String>()
            for (i in movie.genres) {
                val actor = genres.filter {
                    it.id==i
                }
                genresAux.add(genres[i].name)
            }

            val intent = Intent(this@MovieList, ViewMovie::class.java)
            intent.putExtra("Title", movie.title)
            intent.putExtra("Description", movie.description)
            intent.putExtra("Director", movie.director)
            intent.putExtra("Year", movie.year.toString())
            intent.putExtra("Rating", movie.rating.toString())
            intent.putExtra("Votes", movie.votes.toString())
            intent.putExtra("Revenue", movie.revenue.toString())
            intent.putExtra("Length", movie.runtime.toString())
            intent.putExtra("Actors", actorsAux.toString())
            intent.putExtra("Genres", genresAux.toString())
            startActivity(intent)
            adapter.getView(position, view, parent)
            Log.d("RESULT", "Data updated")
        }
        movie_list.setOnItemLongClickListener { parent, view, position, id ->
            Toast.makeText(this@MovieList, "Long click", Toast.LENGTH_LONG).show()
            val intent = Intent(this, EditMovie::class.java)
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