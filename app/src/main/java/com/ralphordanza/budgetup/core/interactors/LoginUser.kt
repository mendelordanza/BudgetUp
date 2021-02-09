package com.ralphordanza.budgetup.core.interactors

import com.google.firebase.auth.AuthResult
import com.ralphordanza.budgetup.core.data.repository.UserRepository
import com.ralphordanza.budgetup.core.domain.model.Resource
import javax.inject.Inject

class LoginUser @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(email: String, password: String): Resource<AuthResult> {
        if(email.isEmpty() || email.isBlank()){
            return Resource.error("Email is required", null)
        }

        if(password.isEmpty() || password.isBlank()){
            return Resource.error("Email is required", null)
        }

        return userRepository.login(email, password)
    }
}