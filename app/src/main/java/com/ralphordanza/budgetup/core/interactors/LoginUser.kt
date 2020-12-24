package com.ralphordanza.budgetup.core.interactors

import com.google.firebase.auth.AuthResult
import com.ralphordanza.budgetup.core.data.repository.UserRepository
import com.ralphordanza.budgetup.core.domain.model.Failed
import com.ralphordanza.budgetup.core.domain.model.Result
import java.lang.Exception
import javax.inject.Inject

class LoginUser @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(email: String, password: String): Result<AuthResult> {
        if(email.isEmpty() || email.isBlank()){
            return Failed(Exception("Email is required"))
        }

        if(password.isEmpty() || password.isBlank()){
            return Failed(Exception("Password is required"))
        }

        return userRepository.login(email, password)
    }
}