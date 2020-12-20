package com.ralphordanza.budgetup.core.data.datasource

import com.google.firebase.firestore.DocumentReference
import com.ralphordanza.budgetup.core.domain.Result
import com.ralphordanza.budgetup.core.domain.Wallet

interface WalletDataSource {

    suspend fun getWallets(userId: String): List<Wallet>

    suspend fun addWallet(userId: String, walletName: String, initialAmt: String): Result<DocumentReference>

    suspend fun adjustWallet(wallet: Wallet)

    suspend fun deleteWallet(wallet: Wallet)
}