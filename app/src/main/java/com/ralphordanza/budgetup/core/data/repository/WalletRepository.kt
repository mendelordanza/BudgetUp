package com.ralphordanza.budgetup.core.data.repository

import com.ralphordanza.budgetup.core.data.datasource.WalletDataSource
import com.ralphordanza.budgetup.core.domain.model.Wallet
import javax.inject.Inject

class WalletRepository @Inject constructor(private val walletDataSource: WalletDataSource) {

    suspend fun getWallets(userId: String) = walletDataSource.getWallets(userId)

    suspend fun getTotal(userId: String) = walletDataSource.getTotal(userId)

    suspend fun addWallet(userId: String, walletName: String, initialAmt: String) =
        walletDataSource.addWallet(userId, walletName, initialAmt)

    suspend fun adjustWallet(wallet: Wallet) = walletDataSource.adjustWallet(wallet)

    suspend fun deleteWallet(userId: String, wallet: Wallet) = walletDataSource.deleteWallet(userId, wallet)
}