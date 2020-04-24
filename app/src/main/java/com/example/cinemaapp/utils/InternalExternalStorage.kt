package com.example.cinemaapp.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cinemaapp.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_movie_list.*

const val KEY = "value"
const val PREFERENCES = "example_preferences"
const val DEFAULT = "No value stored"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!read().trim().isNullOrEmpty()) {
            txtOutputValue.text = read()
        } else {
            txtOutputValue.text = DEFAULT
        }
        btnSave.setOnClickListener {
            val value = txtInputValue.text.toString()
            if (!value.trim().isNullOrEmpty()) {
                write(value)
                reset()
                txtOutputValue.text = read()
            }
        }
    }

    private fun reset() {
        txtInputValue.setText("")
    }

    private fun write(content: String) {
        val sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(KEY, content).apply()
    }

    private fun read(): String {
        val sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY, DEFAULT)!!
    }
}
