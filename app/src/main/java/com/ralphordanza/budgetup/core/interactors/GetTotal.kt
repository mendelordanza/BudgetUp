package com.ralphordanza.budgetup.core.interactors

import com.ralphordanza.budgetup.core.data.repository.WalletRepository
import javax.inject.Inject

class GetTotal @Inject constructor(private val walletRepository: WalletRepository) {
    suspend operator fun invoke(userId: String) = walletRepository.getTotal(userId)
}