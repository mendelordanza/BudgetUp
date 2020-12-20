package com.ralphordanza.budgetup.core.data.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentReference
import com.ralphordanza.budgetup.core.data.datasource.UserDataSource
import com.ralphordanza.budgetup.core.domain.Result
import com.ralphordanza.budgetup.core.domain.User
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDataSource: UserDataSource) {

    suspend fun register(user: User) = userDataSource.register(user)

    suspend fun saveToFirestore(user: User) = userDataSource.saveToFirestore(user)

    suspend fun login(email: String, password: String) = userDataSource.login(email, password)

    suspend fun logout() = userDataSource.logout()
}