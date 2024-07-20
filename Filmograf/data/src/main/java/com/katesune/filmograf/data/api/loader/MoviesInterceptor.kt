package com.katesune.filmograf.data.api.loader

import com.katesune.filmograf.data.api.InterceptorWithProps

class MoviesInterceptor : InterceptorWithProps() {
    private val selectFields = listOf(
        "id", "name", "description", "shortDescription", "slogan", "year", "rating", "genres", "poster", "persons"
    )

    private val notNullName = "name"
    private val notNullPoster = "poster.url"

    override val queryProps = mapOf(
        "selectFields" to selectFields.toString(),
        "notNullFields" to notNullName,
        "notNullFields" to notNullPoster,
    )
}