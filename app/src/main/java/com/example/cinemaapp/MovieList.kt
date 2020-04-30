package com.example.cinemaapp

import android.os.Bundle
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_movie_list.*
import com.example.cinemaapp.utils.CustomAdapterMovies
import com.example.cinemaapp.utils.api.getAPI
import com.example.cinemaapp.utils.api.getAPI.datos

const val ID = "ID"
const val EDIT_CODE = 27

class MovieList : AppCompatActivity() {
    lateinit var adapter: CustomAdapterMovies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        getAPI.getDataFromAPI()
        Log.d("DATOS " + datos.size, datos.toString())
        adapter = CustomAdapterMovies(context = this, resourceId = R.layout.row_element_movie, items = datos)
        movie_list.adapter = this.adapter
        movie_list.setOnItemClickListener { parent, view, position, _ ->
            val intent = Intent(this, ViewMovie::class.java)
            val movie = datos[position]
            intent.putExtra(ID, movie.id)
            startActivityForResult(intent, EDIT_CODE)
            adapter.getView(position, view, parent)
            Log.d("RESULT", "Data updated")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_CODE)
            adapter.notifyDataSetChanged()
    }
}