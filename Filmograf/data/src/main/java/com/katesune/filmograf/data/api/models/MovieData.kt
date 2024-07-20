package com.katesune.filmograf.data.api.models
class MovieData (
    var id: Int = 0,
    var title: String = "",
    var year: String = "",
    var description: String = "",
    var slogan: String = "",
    var rating: Float = 0f,
    var genres: List<String> = listOf(),
    var posterUri: String = "",
    var persons: List<PersonData> = listOf(),
    var shortDescription: String = "",
)

class PersonData (
    var id: Int = 0,
    var name: String = "",
    var photoUrl: String = ""
)