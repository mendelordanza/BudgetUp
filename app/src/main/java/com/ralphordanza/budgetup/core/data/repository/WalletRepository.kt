package com.ralphordanza.budgetup.core.data.repository

import com.ralphordanza.budgetup.core.data.datasource.WalletDataSource
import com.ralphordanza.budgetup.core.domain.Wallet
import javax.inject.Inject

class WalletRepository @Inject constructor(private val walletDataSource: WalletDataSource) {

    suspend fun addWallet(wallet: Wallet) = walletDataSource.addWallet(wallet)

    suspend fun adjustWallet(wallet: Wallet) = walletDataSource.adjustWallet(wallet)

    suspend fun deleteWallet(wallet: Wallet) = walletDataSource.deleteWallet(wallet)
}