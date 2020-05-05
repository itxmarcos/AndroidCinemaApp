package com.example.cinemaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cinemaapp.utils.CustomAdapterActor
import com.example.cinemaapp.utils.api.ApiClient
import com.example.cinemaapp.utils.data_model.Actor
import kotlinx.android.synthetic.main.activity_actors_view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class ActorsView : AppCompatActivity() {
    lateinit var adapter: CustomAdapterActor

    val actors by lazy {
        runBlocking{
            initActors()
        }
    }

    suspend fun initActors() : MutableList<Actor> {
        var result = mutableListOf<Actor>()
        withContext(Dispatchers.IO) {
            result.addAll(ApiClient.actors)
        }
        return result
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actors_view)

        adapter = CustomAdapterActor(context = this@ActorsView, resourceId = R.layout.row_element, items = actors)
        actors_list.adapter = this@ActorsView.adapter

        //Faltaría ver a qué películas pertenece cada actor
    }
}
