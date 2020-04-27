package com.example.cinemaapp.utils.model

data class Actor(var id: Int, var name: String) {
    override fun toString(): String {
        return "$id.- Actor name: $name"
    }
}