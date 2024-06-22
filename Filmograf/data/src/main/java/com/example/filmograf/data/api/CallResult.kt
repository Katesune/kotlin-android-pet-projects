package com.example.filmograf.data.api

import com.example.filmograf.data.api.loader.MoviesResponse
import retrofit2.HttpException
import retrofit2.Response

sealed interface CallResult<T: Any> {
    class Success<T: Any>(val response: T): CallResult<T>
    class Error<T: Any>(val code: Int, val message: String): CallResult<T>
    class Exception<T: Any>(val e: Throwable): CallResult<T>
}

interface ParsableResponse {
    fun parseResponseData(response: Response<MoviesResponse>): CallResult<MoviesResponse> {
        return try {
            val body = response.body()

            if (response.isSuccessful && body != null) {
                CallResult.Success(body)
            } else {
                CallResult.Error(code = response.code(), message = response.message())
            }

        } catch (e: HttpException) {
            CallResult.Error(code = response.code(), message = response.message())
        }
        catch(e: Throwable) {
            CallResult.Exception(e)
        }
    }

}