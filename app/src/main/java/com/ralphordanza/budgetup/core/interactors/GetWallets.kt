package com.ralphordanza.budgetup.core.interactors

import androidx.lifecycle.ViewModel
import com.ralphordanza.budgetup.core.data.repository.WalletRepository
import javax.inject.Inject

class GetWallets @Inject constructor(private val walletRepository: WalletRepository) : ViewModel() {
    suspend operator fun invoke(userId: String) = walletRepository.getWallets(userId)
}