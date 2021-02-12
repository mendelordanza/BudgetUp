package com.ralphordanza.budgetup.core.interactors

import com.google.firebase.firestore.DocumentReference
import com.ralphordanza.budgetup.core.data.repository.WalletRepository
import com.ralphordanza.budgetup.core.domain.model.Resource
import com.ralphordanza.budgetup.framework.utils.Constants.AMOUNT
import com.ralphordanza.budgetup.framework.utils.Constants.WALLET_NAME
import javax.inject.Inject

class AddWallet @Inject constructor(private val walletRepository: WalletRepository) {
    suspend operator fun invoke(userId: String, walletName: String, initialAmt: String): Resource<String> {

        if(walletName.isEmpty()){
            return Resource.error("Wallet Name is required", WALLET_NAME)
        }

        if(initialAmt.isEmpty()){
            return Resource.error("Amount is required", AMOUNT)
        }

        return walletRepository.addWallet(userId, walletName, initialAmt)
    }
}