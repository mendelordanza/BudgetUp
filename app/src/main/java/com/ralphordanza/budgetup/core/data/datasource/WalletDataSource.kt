package com.ralphordanza.budgetup.core.data.datasource

import com.ralphordanza.budgetup.core.domain.Wallet

interface WalletDataSource {

    suspend fun addWallet(wallet: Wallet)

    suspend fun adjustWallet(wallet: Wallet)

    suspend fun deleteWallet(wallet: Wallet)
}