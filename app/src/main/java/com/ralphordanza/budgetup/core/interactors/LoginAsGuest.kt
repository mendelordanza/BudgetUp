package com.ralphordanza.budgetup.core.interactors

import com.ralphordanza.budgetup.core.data.repository.UserRepository
import javax.inject.Inject

class LoginAsGuest @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke() = userRepository.loginAsGuest()
}