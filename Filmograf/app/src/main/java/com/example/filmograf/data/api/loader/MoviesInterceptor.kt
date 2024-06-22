package com.example.filmograf.data.api.loader

import com.example.filmograf.data.api.InterceptorWithProps

class MoviesInterceptor : InterceptorWithProps() {
    private val selectFields = listOf(
        "id", "name", "description", "shortDescription", "slogan", "year", "rating", "genres", "poster", "persons"
    )

    override val queryProps = mapOf("selectFields" to selectFields.toString())
}