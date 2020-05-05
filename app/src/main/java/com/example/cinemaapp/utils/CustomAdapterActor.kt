package com.example.cinemaapp.utils

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.cinemaapp.utils.data_model.Actor
import kotlinx.android.synthetic.main.activity_view_movie.view.*
import kotlinx.android.synthetic.main.activity_view_movie.view.textView
import kotlinx.android.synthetic.main.row_element.view.*

class CustomAdapterActor : ArrayAdapter<Actor> {
    val resourceId: Int

    class ViewHolder {
        lateinit var nameActor : TextView
    }

    constructor(context: Context, resourceId: Int, items: MutableList<Actor>) : super(context, resourceId, items) {
        this.resourceId = resourceId
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = inflater.inflate(resourceId, null)

        val viewHolder = ViewHolder()
        viewHolder.nameActor = view.textView as TextView
        //viewHolder.descriptionMovie = view.descriptionMovie as TextView

        view.setTag(viewHolder)
        val value = getItem(position)
        val holder = view!!.tag as ViewHolder
        holder.nameActor.text = "${value!!.name}"
        Log.d("ADAPTER GET VIEW", value!!.name)
        return view
    }
}