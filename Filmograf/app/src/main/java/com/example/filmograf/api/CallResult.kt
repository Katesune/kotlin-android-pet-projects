package com.example.filmograf.api

sealed interface CallResult<T: Any> {
    class Success<T: Any>(val response: T): CallResult<T>
    class Error<T: Any>(val code: Int, val message: String): CallResult<T>
    class Exception<T: Any>(val e: Throwable): CallResult<T>
}