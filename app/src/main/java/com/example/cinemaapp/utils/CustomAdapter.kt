package com.example.cinemaapp.utils

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.cinemaapp.utils.data_model.Movie
import kotlinx.android.synthetic.main.row_element_movie.view.*

class CustomAdapterMovies : ArrayAdapter<Movie> {
    val resourceId: Int
    class ViewHolder {
        lateinit var titleMovie : TextView
    }

    //items antes era una MutableList
    constructor(context: Context, resourceId: Int, items: Array<Movie>) : super(context, resourceId, items) {
        this.resourceId = resourceId
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val timestamp = System.currentTimeMillis()
        if(view == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(resourceId, null)
            Log.d("ADAPTER NEW VIEW", "New view ${System.currentTimeMillis() - timestamp}")
            val viewHolder = ViewHolder()
            viewHolder.titleMovie = view.titleMovie as TextView
        } else {
            Log.d("ADAPTER REUSED VIEW", "Reused view ${System.currentTimeMillis() - timestamp}")
        }
        val value = getItem(position)
        val holder = view!!.tag as ViewHolder
        holder.titleMovie.text = "T2 ${value!!.title}"
        Log.d("ADAPTER GET VIEW", value!!.title)
        return view
    }
}