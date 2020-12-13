package com.ralphordanza.budgetup.core.data.repository

import com.ralphordanza.budgetup.core.data.datasource.UserDataSource
import com.ralphordanza.budgetup.core.domain.User
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDataSource: UserDataSource) {

    suspend fun register(user: User) = userDataSource.register(user)

    suspend fun login(email: String, password: String) = userDataSource.login(email, password)

    suspend fun logout() = userDataSource.logout()
}