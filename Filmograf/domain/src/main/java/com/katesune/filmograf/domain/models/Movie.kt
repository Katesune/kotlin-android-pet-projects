package com.katesune.filmograf.domain.models

class Movie (
    var id: Int = 0,
    var title: String = "",
    var year: String = "",
    var description: String = "",
    var slogan: String = "",
    var rating: Float = 0f,
    var genres: List<String> = listOf(),
    var posterUri: String = "",
    var persons: List<Person> = listOf(),
    var shortDescription: String = "",
) {
    val imagesUrls = listOf(posterUri) + persons.map { it.photoUrl }
}

class Person(
    var id: Int = 0,
    var name: String = "",
    var photoUrl: String = ""
)