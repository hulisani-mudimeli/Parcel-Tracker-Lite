package com.offerzen.common.result

sealed class MyResult<out T> {
    data class Success<T>(val data: T) : MyResult<T>()
    data class Error(val cause: Throwable) : MyResult<Nothing>()
}