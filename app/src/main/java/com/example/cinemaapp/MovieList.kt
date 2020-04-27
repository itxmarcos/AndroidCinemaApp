package com.example.cinemaapp

import android.os.Build.ID
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.cinemaapp.utils.model.Movie
import kotlinx.android.synthetic.main.activity_movie_list.*

class MovieList : AppCompatActivity() {

    lateinit var movie : Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        val id = intent.getStringExtra(ID)
        //movie = People.persons.first { it.id == id }

        et1.setText(movie.title)
        et2.setText(movie.id)
        //et3.setText(movie.genres)

        et1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                movie.title = et1.text.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

        })
    }
}
