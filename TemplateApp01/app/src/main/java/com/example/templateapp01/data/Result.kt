package com.example.templateapp01.data

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * A generic class that holds a value or an exception
 */
sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
    object UnAuthorizedError : Result<Nothing>()
    object BadRequestError : Result<Nothing>()
    object NotFoundError : Result<Nothing>()
}

fun <T> Result<T>.successOr(fallback: T): T {
    return (this as? Result.Success<T>)?.data ?: fallback
}

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> T
): Result<T> {
    return withContext(dispatcher) {
        Log.w("safeApiCall", "currentThread: " + Thread.currentThread().name)
        try {
            Result.Success(apiCall.invoke())
        } catch (e: Throwable) {
            when (e) {
                is HttpException -> {
                    when (e.code()) {
                        HttpURLConnection.HTTP_UNAUTHORIZED -> Result.UnAuthorizedError
                        HttpURLConnection.HTTP_BAD_REQUEST -> Result.BadRequestError
                        HttpURLConnection.HTTP_NOT_FOUND -> Result.NotFoundError
                        else -> Result.Error(e)
                    }
                }
                is UnknownHostException, is ConnectException, is SocketTimeoutException -> {
                    Result.Error(e)
                }
                else -> {
                    Result.Error(e)
                }
            }
        }
    }
}
