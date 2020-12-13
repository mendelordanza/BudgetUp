package com.ralphordanza.budgetup.core.data.datasource

import com.google.firebase.auth.AuthResult
import com.ralphordanza.budgetup.core.domain.Result
import com.ralphordanza.budgetup.core.domain.User

interface UserDataSource {

    suspend fun register(user: User)

    suspend fun login(email: String, password: String): Result<AuthResult>

    suspend fun logout()
}