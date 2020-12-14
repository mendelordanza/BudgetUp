package com.ralphordanza.budgetup.core.interactors

import com.google.firebase.auth.AuthResult
import com.ralphordanza.budgetup.core.data.repository.UserRepository
import com.ralphordanza.budgetup.core.domain.Result
import com.ralphordanza.budgetup.core.domain.User
import javax.inject.Inject

class RegisterUser @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(user: User): Result<AuthResult> = userRepository.register(user)
}