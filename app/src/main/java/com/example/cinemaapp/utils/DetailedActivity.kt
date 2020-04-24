package com.example.cinemaapp.utils

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.cinemaapp.R
import com.example.cinemaapp.utils.model.People
import com.example.cinemaapp.utils.model.Person
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    lateinit var person : Person

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val id = intent.getStringExtra(ID)
        person = People.persons.first { it.id == id }
        et1.setText(person.name)
        et2.setText(person.familyName)
        et1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                person.name = et1.text.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

        })
    }
}
