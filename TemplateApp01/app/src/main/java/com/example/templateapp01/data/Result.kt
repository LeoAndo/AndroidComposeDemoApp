package com.example.templateapp01.data

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.lang.Exception
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * A generic class that holds a value or an exception
 */
internal sealed class SafeResult<out R> {
    data class Success<out T>(val data: T) : SafeResult<T>()
    data class Error(val errorResult: ErrorResult) : SafeResult<Nothing>()
}

internal sealed class ErrorResult : Exception() {
    data class UnAuthorizedError(override val message: String? = "UnAuthorizedError") :
        ErrorResult()

    data class BadRequestError(override val message: String? = "BadRequestError") :
        ErrorResult()

    data class NotFoundError(override val message: String? = "NotFoundError") :
        ErrorResult()

    data class NetworkError(override val message: String? = "NetworkError") :
        ErrorResult()

    data class OtherError(override val message: String? = "OtherError") :
        ErrorResult()
}

internal inline fun <T> SafeResult<T>.fold(
    onSuccess: (value: T) -> Unit,
    onFailure: (ErrorResult) -> Unit,
): SafeResult<T> {
    when (this) {
        is SafeResult.Error -> {
            onFailure(errorResult)
            Log.e("fold", "error: " + errorResult.localizedMessage)
        }
        is SafeResult.Success -> {
            onSuccess(data)
        }
    }
    return this
}

internal suspend fun <T> safeCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): SafeResult<T> {
    return withContext(dispatcher) {
        Log.d("safeCall", "currentThread: " + Thread.currentThread().name)
        try {
            SafeResult.Success(apiCall.invoke())
        } catch (e: Throwable) {
            Log.e("safeCall", "error: " + e.localizedMessage)
            when (e) {
                is HttpException -> {
                    when (e.code()) {
                        HttpURLConnection.HTTP_UNAUTHORIZED -> {
                            SafeResult.Error(
                                ErrorResult.UnAuthorizedError(
                                    e.localizedMessage
                                )
                            )
                        }
                        HttpURLConnection.HTTP_BAD_REQUEST -> {
                            SafeResult.Error(
                                ErrorResult.BadRequestError(
                                    e.localizedMessage
                                )
                            )
                        }
                        HttpURLConnection.HTTP_NOT_FOUND -> {
                            SafeResult.Error(
                                ErrorResult.NotFoundError(
                                    e.localizedMessage
                                )
                            )
                        }
                        else -> {
                            SafeResult.Error(
                                ErrorResult.OtherError(
                                    e.localizedMessage
                                )
                            )
                        }
                    }
                }
                is UnknownHostException, is ConnectException, is SocketTimeoutException -> {
                    SafeResult.Error(
                        ErrorResult.NetworkError(
                            e.localizedMessage
                        )
                    )
                }
                else -> {
                    SafeResult.Error(
                        ErrorResult.OtherError(
                            e.localizedMessage
                        )
                    )
                }
            }
        }
    }
}
