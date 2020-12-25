package com.ralphordanza.budgetup.core.data.repository

import com.ralphordanza.budgetup.core.data.datasource.UserDataSource
import com.ralphordanza.budgetup.core.domain.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDataSource: UserDataSource) {

    suspend fun register(email: String, password: String) = userDataSource.register(email, password)

    suspend fun saveToFirestore(user: User) = userDataSource.saveToFirestore(user)

    suspend fun login(email: String, password: String) = userDataSource.login(email, password)

    suspend fun loginAsGuest() = userDataSource.loginAsGuest()

    suspend fun logout() = userDataSource.logout()
}