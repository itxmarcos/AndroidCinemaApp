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

        txt_title.setText(intent.getStringExtra("Title"))
        txt_description.setText(intent.getStringExtra("Description"))
        txt_director.setText(intent.getStringExtra("Director"))
        txt_year.setText(intent.getStringExtra("Year"))
        txt_length.setText(intent.getStringExtra("Length"))
        txt_rating.setText(intent.getStringExtra("Rating"))
        txt_votes.setText(intent.getStringExtra("Votes"))
        txt_revenue.setText(intent.getStringExtra("Revenue"))
        txt_actors.setText(intent.getStringExtra("Actors"))
        txt_genres.setText(intent.getStringExtra("Genres"))
    }
}