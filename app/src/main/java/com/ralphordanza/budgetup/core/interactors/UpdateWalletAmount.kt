package com.ralphordanza.budgetup.core.interactors

import com.ralphordanza.budgetup.core.data.repository.WalletRepository
import javax.inject.Inject

class UpdateWalletAmount @Inject constructor(private val walletRepository: WalletRepository) {

    suspend operator fun invoke(updateAmt: String, walletId: String, userId: String) =
        walletRepository.updateWalletAmount(updateAmt, walletId, userId)
}