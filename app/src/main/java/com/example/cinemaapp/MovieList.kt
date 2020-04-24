package com.example.cinemaapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class MovieList : AppCompatActivity() {

    val dataSource = mutableListOf(Contact("María", "1"), Contact("Marcos","2"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ContactAdapter(this, dataSource)

        namesListView.adapter = adapter

    }
}
data class Contact(val name: String, val number: String)

class ContactAdapter(val context: Context, val elements: List<Contact>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = View.inflate(context, R.layout.contact_item_layout, null)
        val contact = getItem(position)
        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvNumber = view.findViewById<TextView>(R.id.tvNumber)
        tvName.text = contact.name
        tvNumber.text = contact.number
        return view
    }

    override fun getItem(position: Int): Contact {
        return elements[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return elements.size
    }

}
