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

class CustomAdapterMovie : ArrayAdapter<Movie> {
    val resourceId: Int

    class ViewHolder {
        lateinit var titleMovie : TextView
        lateinit var descriptionMovie : TextView
        lateinit var directorMovie : TextView
        lateinit var yearMovie : TextView
        lateinit var lengthMovie : TextView
        lateinit var ratingMovie : TextView
        lateinit var votesMovie : TextView
        lateinit var revenueMovie : TextView
        lateinit var genresMovie : TextView
        lateinit var actorsMovie : TextView
    }

    constructor(context: Context, resourceId: Int, items: MutableList<Movie>) : super(context, resourceId, items) {
        this.resourceId = resourceId
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = inflater.inflate(resourceId, null)

        val viewHolder = ViewHolder()
        viewHolder.titleMovie = view.titleMovie as TextView
        //viewHolder.descriptionMovie = view.descriptionMovie as TextView

        view.setTag(viewHolder)
        val value = getItem(position)
        val holder = view!!.tag as ViewHolder
        holder.titleMovie.text = "${value!!.title}"
        Log.d("ADAPTER GET VIEW", value!!.title)
        return view
    }
}