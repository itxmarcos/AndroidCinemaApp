package com.example.cinemaapp.utils.model

data class Movie(var id: Int, var title: String, var genres: List<Int>, var description: String, var director: String, var actors: List<Int>, var year: Int, var length: Int, var rating: Float, var votes: Int, var revenue: Float) {
    override fun toString(): String {
        return "$id.- Movie tittle: $title"
    }
}