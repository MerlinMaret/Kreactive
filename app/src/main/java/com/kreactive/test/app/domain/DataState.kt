package com.kreactive.test.app.domain

data class DataState<T>(
    val data: T? = null,
    val error: Throwable? = null,
    val status: NetworkStatus = NetworkStatus.EMPTY
) {

    enum class NetworkStatus {
        SUCCESS,
        ERROR,
        LOADING,
        EMPTY
    }

    fun success(): DataState<T> {
        return this.copy(
            error = null,
            status = NetworkStatus.SUCCESS
        )
    }

    fun success(data: T): DataState<T> {
        return this.copy(
            data = data,
            error = null,
            status = NetworkStatus.SUCCESS
        )
    }

    fun error(error: Throwable?): DataState<T> {
        return this.copy(
            error = error,
            status = NetworkStatus.ERROR
        )
    }

    fun loading(): DataState<T> {
        return this.copy(
            status = NetworkStatus.LOADING
        )
    }
}