package com.ralphordanza.budgetup.core.data.datasource

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.ralphordanza.budgetup.core.domain.model.Resource
import com.ralphordanza.budgetup.core.domain.model.User

interface UserDataSource {

    suspend fun register(email: String, password: String): Resource<AuthResult>

    suspend fun saveToFirestore(user: User): Resource<Boolean>

    suspend fun login(email: String, password: String): Resource<AuthResult>

    suspend fun loginAsGuest(): Resource<AuthResult>

    suspend fun logout(): Resource<FirebaseUser?>
}