package com.example.cinemaapp.utils

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.cinemaapp.R
import com.example.cinemaapp.utils.model.People
import kotlinx.android.synthetic.main.activity_movie_list.*

const val ID = "ID"
const val EDIT_CODE = 27

class CustomDataModel : AppCompatActivity() {

    lateinit var adapter : CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        adapter = CustomAdapter(context = this, resourceId = R.layout.row_element, items = People.persons)
        lvItems.adapter = this.adapter
        lvItems.setOnItemClickListener { parent, view, position, _ ->
            val intent = Intent(this, DetailActivity::class.java)
            val person = People.persons[position]
            intent.putExtra(ID, person.id)
            startActivityForResult(intent, EDIT_CODE)
            adapter.getView(position, view, parent)
            Log.d("RESULT", "Data updated")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == EDIT_CODE)
            adapter.notifyDataSetChanged()
    }
}
