package com.ralphordanza.budgetup.core.data.datasource

import com.google.firebase.firestore.DocumentReference
import com.ralphordanza.budgetup.core.domain.model.Resource
import com.ralphordanza.budgetup.core.domain.model.Wallet

interface WalletDataSource {

    suspend fun getWallets(userId: String): List<Wallet>

    suspend fun getTotal(userId: String): Double

    suspend fun addWallet(userId: String, walletName: String, initialAmt: String): Resource<String>

    suspend fun adjustWallet(wallet: Wallet)

    suspend fun updateWalletAmount(updatedAmt: String, walletId: String, userId: String): Resource<String>

    suspend fun deleteWallet(userId: String, wallet: Wallet): Resource<String>
}