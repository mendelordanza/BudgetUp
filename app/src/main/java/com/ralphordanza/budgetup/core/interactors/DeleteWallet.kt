package com.ralphordanza.budgetup.core.interactors

import com.ralphordanza.budgetup.core.data.repository.WalletRepository
import com.ralphordanza.budgetup.core.domain.model.Wallet
import javax.inject.Inject

class DeleteWallet @Inject constructor(private val walletRepository: WalletRepository) {

    suspend operator fun invoke(userId: String, wallet: Wallet) = walletRepository.deleteWallet(userId, wallet)
}