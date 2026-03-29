package com.offerzen.common.result

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val cause: Throwable) : Result<Nothing>()
}