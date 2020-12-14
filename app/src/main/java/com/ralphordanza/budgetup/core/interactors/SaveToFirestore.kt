package com.ralphordanza.budgetup.core.interactors

import com.ralphordanza.budgetup.core.data.repository.UserRepository
import com.ralphordanza.budgetup.core.domain.User
import javax.inject.Inject

class SaveToFirestore @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(user: User) = userRepository.saveToFirestore(user)
}