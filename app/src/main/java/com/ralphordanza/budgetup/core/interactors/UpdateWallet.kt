package com.ralphordanza.budgetup.core.interactors

import com.ralphordanza.budgetup.core.data.repository.WalletRepository
import javax.inject.Inject

class UpdateWallet @Inject constructor(private val walletRepository: WalletRepository) {

    suspend operator fun invoke(updatedAmt: String, updatedName: String, walletId: String, userId: String) =
        walletRepository.updateWallet(updatedAmt, updatedName, walletId, userId)
}