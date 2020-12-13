package com.ralphordanza.budgetup.core.data.implementation

import com.ralphordanza.budgetup.core.data.datasource.WalletDataSource
import com.ralphordanza.budgetup.core.domain.Wallet

class WalletDataSourceImpl() : WalletDataSource {

    override suspend fun addWallet(wallet: Wallet) {

    }

    override suspend fun adjustWallet(wallet: Wallet) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteWallet(wallet: Wallet) {
        TODO("Not yet implemented")
    }
}