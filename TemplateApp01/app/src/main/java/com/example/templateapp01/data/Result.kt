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
    data class Failure(val failureResult: FailureResult) : SafeResult<Nothing>()
}

internal sealed class FailureResult : Exception() {
    data class UnAuthorizedFailure(override val message: String? = "UnAuthorizedError") :
        FailureResult()

    data class BadRequestFailure(override val message: String? = "BadRequestError") :
        FailureResult()

    data class NotFoundFailure(override val message: String? = "NotFoundError") :
        FailureResult()

    data class NetworkFailure(override val message: String? = "NetworkError") :
        FailureResult()

    data class OtherFailure(override val message: String? = "OtherError") :
        FailureResult()
}

internal inline fun <T> SafeResult<T>.fold(
    onSuccess: (value: T) -> Unit,
    onFailure: (FailureResult) -> Unit,
): SafeResult<T> {
    val success = this as? SafeResult.Success
    val failure = this as? SafeResult.Failure
    success?.let { onSuccess(success.data) }
    failure?.let {
        onFailure(failure.failureResult)
        Log.e("fold", "error: " + failure.failureResult.localizedMessage)
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
                            SafeResult.Failure(
                                FailureResult.UnAuthorizedFailure(
                                    e.localizedMessage
                                )
                            )
                        }
                        HttpURLConnection.HTTP_BAD_REQUEST -> {
                            SafeResult.Failure(
                                FailureResult.BadRequestFailure(
                                    e.localizedMessage
                                )
                            )
                        }
                        HttpURLConnection.HTTP_NOT_FOUND -> {
                            SafeResult.Failure(
                                FailureResult.NotFoundFailure(
                                    e.localizedMessage
                                )
                            )
                        }
                        else -> {
                            SafeResult.Failure(
                                FailureResult.OtherFailure(
                                    e.localizedMessage
                                )
                            )
                        }
                    }
                }
                is UnknownHostException, is ConnectException, is SocketTimeoutException -> {
                    SafeResult.Failure(
                        FailureResult.NetworkFailure(
                            e.localizedMessage
                        )
                    )
                }
                else -> {
                    SafeResult.Failure(
                        FailureResult.OtherFailure(
                            e.localizedMessage
                        )
                    )
                }
            }
        }
    }
}
