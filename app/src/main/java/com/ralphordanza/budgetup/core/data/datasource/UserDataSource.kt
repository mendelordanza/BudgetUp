package com.ralphordanza.budgetup.core.data.datasource

import com.ralphordanza.budgetup.core.domain.User

interface UserDataSource {

    suspend fun register(user: User)

    suspend fun login(email: String, password: String)

    suspend fun logout()
}