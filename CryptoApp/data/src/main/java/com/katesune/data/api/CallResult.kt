package com.katesune.data.api

import retrofit2.HttpException
import retrofit2.Response

private const val TAG = "CallResult"

sealed interface CallResult<T: Any> {
    class Success<T: Any>(val response: T): CallResult<T>
    class Error<T: Any>(val code: Int, val message: String): CallResult<T>
    class Exception<T: Any>(val e: Throwable): CallResult<T>
}

class RetrofitResponseHandler<T: Any>(private val response: Response<T>) {
    fun handleResponse(): CallResult<T> {
        return try {
            val body = response.body()

            if (response.isSuccessful && body != null) {
                CallResult.Success(body)
            } else {
                CallResult.Error(code = response.code(), message = response.message())
            }

        } catch (httpException: HttpException) {
            CallResult.Error(code = response.code(), message = response.message())
        }
        catch(e: Throwable) {
            CallResult.Exception(e)
        }
    }
}