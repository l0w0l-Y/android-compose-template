package com.kaleksandra.coredata.network

import com.kaleksandra.coredata.network.ApiException.Companion.toApiException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response

sealed class Effect<out T>
data class Success<T>(val data: T) : Effect<T>()
data class Error<T>(val exception: Exception, val message: String = "") : Effect<T>()

object Completable

fun <T> Response<T>.toEffect(): Effect<T> {
    return if (isSuccessful) {
        body()?.let { body -> Success(body) } ?: Error(NullPointerException())
    } else {
        Error(code().toApiException())
    }
}

suspend fun <R> call(
    dispatcher: CoroutineDispatcher, call: suspend () -> Response<R>
): Effect<R> = withContext(dispatcher) {
    try {
        call().toEffect()
    } catch (exception: Exception) {
        Error(exception)
    }
}

fun <T> Effect<T>.toCompletable(): Effect<Completable> {
    return when (this) {
        is Error -> Error(this.exception)
        is Success -> Success(Completable)
    }
}

suspend fun <T> Effect<T>.doOnSuccess(call: suspend (T) -> Unit) = this.apply {
    if (this is Success) {
        call(this.data)
    }
}

suspend fun <T> Effect<T>.doOnError(call: suspend (String) -> Unit) = this.apply {
    if (this is Error) {
        call(this.message)
    }
}

suspend fun <T> Effect<T>.flatmap(call: suspend (T?) -> Unit) = this.apply {
    this.doOnSuccess { call(it) }
}

suspend fun <Prev, New> Effect<Prev>.map(call: suspend (Prev?) -> New): Effect<New> {
    return when (this) {
        is Error -> Error(this.exception)
        is Success -> Success(call(this.data))
    }
}

suspend fun tryCatch(
    dispatcher: CoroutineDispatcher, call: suspend () -> Unit
): Effect<Completable> = withContext(dispatcher) {
    try {
        call()
        Success(Completable)
    } catch (exception: Exception) {
        Error(exception)
    }
}