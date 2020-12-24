package com.ralphordanza.budgetup.core.data.datasource

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.ralphordanza.budgetup.core.domain.model.Result
import com.ralphordanza.budgetup.core.domain.model.User

interface UserDataSource {

    suspend fun register(email: String, password: String): Result<AuthResult>

    suspend fun saveToFirestore(user: User): Boolean

    suspend fun login(email: String, password: String): Result<AuthResult>

    suspend fun loginAsGuest(): Result<AuthResult>

    suspend fun logout(): FirebaseUser?
}