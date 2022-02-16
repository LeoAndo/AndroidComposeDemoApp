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
@Deprecated(message = "use kotlin.Result", replaceWith = ReplaceWith("Result", "kotlin.Result"))
internal sealed class SafeResult<out R> {
    data class Success<out T>(val data: T) : SafeResult<T>()
    data class Failure(val failureResult: FailureResult) : SafeResult<Nothing>()
}

internal sealed class FailureResult : Exception() {
    data class UnAuthorized(override val message: String? = "UnAuthorizedError") :
        FailureResult()

    data class BadRequest(override val message: String? = "BadRequestError") :
        FailureResult()

    data class NotFound(override val message: String? = "NotFoundError") :
        FailureResult()

    data class Network(override val message: String? = "NetworkError") :
        FailureResult()

    data class Unknown(override val message: String? = "UnknownError") :
        FailureResult()
}

@Deprecated("use kotlin.Result.fold", replaceWith = ReplaceWith("fold", "kotlin.Result"))
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
): Result<T> {
    return withContext(dispatcher) {
        Log.d("safeCall", "currentThread: " + Thread.currentThread().name)
        try {
            Result.success(apiCall.invoke())
        } catch (e: Throwable) {
            Log.e("safeCall", "error: " + e.localizedMessage)
            when (e) {
                is HttpException -> {
                    when (e.code()) {
                        HttpURLConnection.HTTP_UNAUTHORIZED -> {
                            Result.failure(
                                FailureResult.UnAuthorized(
                                    e.localizedMessage
                                )
                            )
                        }
                        HttpURLConnection.HTTP_BAD_REQUEST -> {
                            Result.failure(
                                FailureResult.BadRequest(
                                    e.localizedMessage
                                )
                            )
                        }
                        HttpURLConnection.HTTP_NOT_FOUND -> {
                            Result.failure(
                                FailureResult.NotFound(
                                    e.localizedMessage
                                )
                            )
                        }
                        else -> {
                            Result.failure(
                                FailureResult.Unknown(
                                    e.localizedMessage
                                )
                            )
                        }
                    }
                }
                is UnknownHostException, is ConnectException, is SocketTimeoutException -> {
                    Result.failure(
                        FailureResult.Network(
                            e.localizedMessage
                        )
                    )
                }
                else -> {
                    Result.failure(
                        FailureResult.Unknown(
                            e.localizedMessage
                        )
                    )
                }
            }
        }
    }
}
