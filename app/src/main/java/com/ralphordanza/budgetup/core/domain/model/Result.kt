package com.ralphordanza.budgetup.core.domain.model

sealed class Result<out T : Any>
data class Success<out T : Any>(val data: T) : Result<T>()
data class Failed(val exception: Throwable, val message: String = exception.localizedMessage ?: "Something went wrong.") : Result<Nothing>()