package com.example.cinemaapp.utils.data_model

import com.example.cinemaapp.utils.api.ApiClient.actors

data class Actor(var id: Int, var name: String) {
    /*override fun toString(): String {
        return "$id.- Actor name: $name"
    }*/

    fun getActorById(id: Int) : Actor? {
        val actorsUn = listOf<Actor>()
        val actor = actors.find {
            it.id == id
        }
        return actor
    }
}