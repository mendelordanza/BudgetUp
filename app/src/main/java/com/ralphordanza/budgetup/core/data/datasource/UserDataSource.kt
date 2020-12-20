package com.ralphordanza.budgetup.core.data.datasource

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.ralphordanza.budgetup.core.domain.Result
import com.ralphordanza.budgetup.core.domain.User

interface UserDataSource {

    suspend fun register(user: User): Result<AuthResult>

    suspend fun saveToFirestore(user: User): Result<DocumentReference>

    suspend fun login(email: String, password: String): Result<AuthResult>

    suspend fun logout(): FirebaseUser?
}