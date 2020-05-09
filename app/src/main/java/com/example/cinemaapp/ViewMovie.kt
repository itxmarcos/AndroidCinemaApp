package com.example.cinemaapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_view_movie.*

class ViewMovie : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_movie)

        var title = intent.getStringExtra("Title")
        var description = intent.getStringExtra("Description")
        var director = intent.getStringExtra("Director")
        var year = intent.getStringExtra("Year")
        var length = intent.getStringExtra("Length")
        var rating = intent.getStringExtra("Rating")
        var votes = intent.getStringExtra("Votes")
        var revenue = intent.getStringExtra("Revenue")
        var actors = intent.getStringExtra("Actors")
        var genres = intent.getStringExtra("Genres")

        txt_title.setText(title)
        txt_description.setText(description)
        txt_director.setText(director)
        txt_year.setText(year)
        txt_length.setText(length)
        txt_rating.setText(rating)
        txt_votes.setText(votes)
        txt_revenue.setText(revenue)
        txt_actors.setText(actors)
        txt_genres.setText(genres)

        txt_actors.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                title = txt_title.text.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        txt_actors.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                description = txt_description.text.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        txt_actors.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                director = txt_director.text.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        txt_actors.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                year = txt_year.text.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        txt_actors.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                length = txt_length.text.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        txt_actors.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                rating = txt_rating.text.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        txt_actors.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                votes = txt_votes.text.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        txt_actors.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                revenue = txt_revenue.text.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        txt_actors.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                actors = txt_actors.text.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        txt_actors.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                genres = txt_genres.text.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })
    }
}