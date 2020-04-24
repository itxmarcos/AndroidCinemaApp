package com.example.cinemaapp.utils.model

object People {

    var persons : MutableList<Person>

    init {
        persons = createSampleData(100)
    }

    private fun createSampleData(sampleSize: Int) = mutableListOf<Person>().apply {
        for (i in 1..sampleSize + 1)
            add(Person("$i", "Name$i", "FamilyName$i", i, "Phone $i"))
    }
}