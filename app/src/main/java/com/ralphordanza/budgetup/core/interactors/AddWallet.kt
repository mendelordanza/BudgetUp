package com.ralphordanza.budgetup.core.interactors

import com.google.firebase.firestore.DocumentReference
import com.ralphordanza.budgetup.core.data.repository.WalletRepository
import com.ralphordanza.budgetup.core.domain.Result
import javax.inject.Inject

class AddWallet @Inject constructor(private val walletRepository: WalletRepository) {
    suspend operator fun invoke(userId: String, walletName: String, initialAmt: String) =
        walletRepository.addWallet(userId, walletName, initialAmt)
}