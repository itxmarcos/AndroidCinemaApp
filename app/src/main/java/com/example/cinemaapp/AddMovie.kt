package com.example.cinemaapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_view_movie.*


class AddMovie : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_add_movie)

            //Esto se suponque que es para cuando cambie el texto que sea consciente de ello
            txt_title.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    title = txt_title.text.toString()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

            })
        }
}
