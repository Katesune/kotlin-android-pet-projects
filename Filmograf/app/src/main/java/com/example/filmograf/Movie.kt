package com.example.filmograf

import android.net.Uri

class Movie (
    var id: Int = 0,
    var title: String = "",
    var year: String = "",
    var description: String = "",
    var slogan: String = "",
    var rating: Float = 0f,
    var genres: List<String> = listOf(),
    var poster: String = "",
    var persons: List<Person> = listOf(),
    var shortDescription: String = "",
) {
    val posterUri = Uri.parse(poster)
        .buildUpon()
}

class Person(
    var id: Int = 0,
    var name: String = "",
    var photo: String = ""
) {
    val photoUrl = Uri.parse(photo).buildUpon()
}