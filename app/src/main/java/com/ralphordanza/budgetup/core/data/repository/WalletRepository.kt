package com.ralphordanza.budgetup.core.data.repository

import com.google.firebase.firestore.DocumentReference
import com.ralphordanza.budgetup.core.data.datasource.WalletDataSource
import com.ralphordanza.budgetup.core.domain.Result
import com.ralphordanza.budgetup.core.domain.Wallet
import javax.inject.Inject

class WalletRepository @Inject constructor(private val walletDataSource: WalletDataSource) {

    suspend fun getWallets(userId: String) = walletDataSource.getWallets(userId)

    suspend fun addWallet(userId: String, walletName: String, initialAmt: String) =
        walletDataSource.addWallet(userId, walletName, initialAmt)

    suspend fun adjustWallet(wallet: Wallet) = walletDataSource.adjustWallet(wallet)

    suspend fun deleteWallet(wallet: Wallet) = walletDataSource.deleteWallet(wallet)
}