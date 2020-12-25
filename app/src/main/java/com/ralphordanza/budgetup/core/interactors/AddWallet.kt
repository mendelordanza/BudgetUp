package com.ralphordanza.budgetup.core.interactors

import com.ralphordanza.budgetup.core.data.repository.WalletRepository
import javax.inject.Inject

class AddWallet @Inject constructor(private val walletRepository: WalletRepository) {
    suspend operator fun invoke(userId: String, walletName: String, initialAmt: String) =
        walletRepository.addWallet(userId, walletName, initialAmt)
}