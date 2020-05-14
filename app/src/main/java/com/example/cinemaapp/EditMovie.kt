package com.example.cinemaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_view_movie.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class EditMovie : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_movie)

        txt_title.setText(intent.getStringExtra("Title"))
        txt_description.setText(intent.getStringExtra("Description"))
        txt_director.setText(intent.getStringExtra("Director"))
        txt_year.setText(intent.getStringExtra("Year"))
        txt_length.setText(intent.getStringExtra("Length"))
        txt_rating.setText(intent.getStringExtra("Rating"))
        txt_votes.setText(intent.getStringExtra("Votes"))
        txt_revenue.setText(intent.getStringExtra("Revenue"))
        //txt_actors.setText(intent.getStringExtra("Actors"))
        //txt_genres.setText(intent.getStringExtra("Genres"))

        //Esto se supone que es para cuando cambie el texto que sea consciente de ello
        txt_title.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                title = txt_title.text.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

        })
    }

    fun post(url: String, body: String): String {
        return URL(url)
            .openConnection()
            .let {
                it as HttpURLConnection
            }.apply {
                setRequestProperty("Content-Type", "application/json; charset=utf-8")
                requestMethod = "POST"
                doOutput = true
                val outputWriter = OutputStreamWriter(outputStream)
                outputWriter.write(body)
                outputWriter.flush()
            }.let {
                if (it.responseCode == 200) it.inputStream else it.errorStream
            }.let { streamToRead ->
                BufferedReader(InputStreamReader(streamToRead)).use {
                    val response = StringBuffer()
                    var inputLine = it.readLine()
                    while (inputLine != null) {
                        response.append(inputLine)
                        inputLine = it.readLine()
                    }
                    it.close()
                    response.toString()
                }
            }
    }
}
