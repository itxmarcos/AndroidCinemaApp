package com.example.cinemaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity: AppCompatActivity() {
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handler= Handler()
        handler.postDelayed({
            val intent = Intent(this, MovieList::class.java)
            startActivity(intent)
            finish()
        }, 3000) //delay of 3 seconds
    }

}

