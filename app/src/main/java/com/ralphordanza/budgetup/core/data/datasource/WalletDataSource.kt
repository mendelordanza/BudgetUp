package com.ralphordanza.budgetup.core.data.datasource

import com.google.firebase.firestore.DocumentReference
import com.ralphordanza.budgetup.core.domain.model.Result
import com.ralphordanza.budgetup.core.domain.model.Wallet

interface WalletDataSource {

    suspend fun getWallets(userId: String): List<Wallet>

    suspend fun getTotal(userId: String): Double

    suspend fun addWallet(userId: String, walletName: String, initialAmt: String): Result<DocumentReference>

    suspend fun adjustWallet(wallet: Wallet)

    suspend fun deleteWallet(wallet: Wallet)
}