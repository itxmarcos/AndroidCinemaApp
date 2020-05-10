package com.example.cinemaapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cinemaapp.utils.CustomAdapterActor
import com.example.cinemaapp.utils.CustomAdapterGenre
import kotlinx.android.synthetic.main.activity_view_movie.*
import com.example.cinemaapp.utils.api.ApiClient
import com.example.cinemaapp.utils.data_model.Actor
import com.example.cinemaapp.utils.data_model.Genre
import com.example.cinemaapp.utils.data_model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class ViewMovie : AppCompatActivity() {
    lateinit var movie : Movie
    lateinit var adapterGenre: CustomAdapterGenre
    lateinit var adapterActor: CustomAdapterActor
    var actorsAux = mutableListOf<Actor>()
    var genresAux = mutableListOf<Genre>()

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
        setContentView(R.layout.activity_view_movie)

        val id = intent.getStringExtra(ID)
        movie = ApiClient.movies.first { it.id == id }

        for (i in movie.actors) {
            val actor = actors.filter {
                it.id==i.toString()
            }
            actorsAux.add(actors[i])
        }

        for (i in movie.genres) {
            val genre = genres.filter {
                it.id==i.toString()
            }
            genresAux.add(genres[i])
        }

        txt_title.setText(movie.title)
        txt_description.setText(movie.description)
        txt_director.setText(movie.director)
        txt_year.setText(movie.year.toString())
        txt_length.setText(movie.runtime.toString())
        txt_rating.setText(movie.rating.toString())
        txt_votes.setText(movie.votes.toString())
        txt_revenue.setText(movie.revenue.toString())
        adapterActor = CustomAdapterActor(context = this@ViewMovie, resourceId = R.layout.row_element, items = actorsAux)
        list_actors.adapter = this@ViewMovie.adapterActor
        list_actors.setOnItemClickListener { parent, view, position, id ->
            val intentActor = Intent(this, ActorsView::class.java)
            val actor = actorsAux[position]
            intentActor.putExtra(ID, actor.id)
            startActivity(intentActor)
            adapterActor.getView(position, view, parent)
        }
        adapterGenre = CustomAdapterGenre(context = this@ViewMovie, resourceId = R.layout.row_element, items = genresAux)
        list_genres.adapter = this@ViewMovie.adapterGenre
        list_genres.setOnItemClickListener { parent, view, position, id ->
            val intentGenre = Intent(this, GenresView::class.java)
            val genre = genresAux[position]
            intentGenre.putExtra(ID, genre.id)
            startActivity(intentGenre)
            adapterGenre.getView(position, view, parent)
        }

        /*txt_title.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                movie.title = txt_title.text.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        txt_description.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                movie.description = txt_description.text.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        txt_director.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                movie.director = txt_director.text.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        txt_year.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                movie.year = txt_year.text.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        txt_length.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                movie.runtime = txt_length.text.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        txt_rating.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                movie.rating = txt_rating.text.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        txt_votes.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                movie.votes = txt_votes.text.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        txt_revenue.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                movie.revenue = txt_revenue.text.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })*/

        /*list_actors.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                actors = list_actors.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        list_genres.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                genres = list_genres.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })*/
    }
}