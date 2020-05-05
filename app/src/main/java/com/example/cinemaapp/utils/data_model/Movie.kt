package com.example.cinemaapp.utils.data_model

data class Movie(var id: Int, var title: String, var description: String, var director: String, var year: Int, var length: Int, var rating: Float, var votes: Int, var revenue: Float, var genres: List<Int>, var actors: List<Int>) {
    /*override fun toString(): String {
        return "$id.- Movie title: $title"
    }*/
}