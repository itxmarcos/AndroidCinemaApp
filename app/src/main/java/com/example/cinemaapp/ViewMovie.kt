package com.example.cinemaapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.cinemaapp.utils.data_model.Movie
import com.example.cinemaapp.utils.api.getAPI.movies
import kotlinx.android.synthetic.main.activity_view_movie.*

class ViewMovie : AppCompatActivity() {
    lateinit var movie : Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_movie)
        val id = intent.getStringExtra(ID)
        movie = movies.first { it.id == id.toInt() } //.toInt()
        et1.setText(movie.title)
        et2.setText(movie.description)
        et3.setText(movie.director)

        /*et1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                person.name = et1.text.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

        })*/
    }
}