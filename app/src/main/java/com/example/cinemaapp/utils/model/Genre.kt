package com.example.cinemaapp.utils.model

data class Genre (var id: Int, var name: String) {
    override fun toString(): String {
        return "$id.- Genre: $name"
    }
}