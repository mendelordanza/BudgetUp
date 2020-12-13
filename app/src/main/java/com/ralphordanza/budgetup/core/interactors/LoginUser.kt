package com.ralphordanza.budgetup.core.interactors

import com.google.firebase.auth.AuthResult
import com.ralphordanza.budgetup.core.data.repository.UserRepository
import com.ralphordanza.budgetup.core.domain.Result
import javax.inject.Inject

class LoginUser @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(email: String, password: String): Result<AuthResult> =
        userRepository.login(email, password)
}