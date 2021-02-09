package com.ralphordanza.budgetup.core.domain.model

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        const val DEFAULT_ERROR_MESSAGE = "Something went wrong"

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

