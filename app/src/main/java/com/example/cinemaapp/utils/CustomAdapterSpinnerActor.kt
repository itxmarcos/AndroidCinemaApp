package com.example.cinemaapp.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.cinemaapp.R
import com.example.cinemaapp.utils.data_model.Actor

class CustomAdapterSpinnerActor(val context: Context, val actors: Array<Actor>) : BaseAdapter() {

    class ViewHolder(option: View?) {
        val nameActor: TextView
        init {
            this.nameActor = option?.findViewById(R.id.textView) as TextView
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view : View
        val viewHolder : ViewHolder
        if(convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.row_element, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view  = convertView
            viewHolder = view.tag as ViewHolder
        }

        viewHolder.nameActor.text = actors[position].name
        return view
    }

    override fun getItem(position: Int): Actor {
        return actors[position]
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    override fun getCount(): Int {
        return actors.size
    }
}