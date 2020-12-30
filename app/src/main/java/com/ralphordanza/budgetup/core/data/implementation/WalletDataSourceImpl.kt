package com.ralphordanza.budgetup.core.data.implementation

import com.google.firebase.firestore.*
import com.ralphordanza.budgetup.core.data.datasource.WalletDataSource
import com.ralphordanza.budgetup.core.domain.model.Failed
import com.ralphordanza.budgetup.core.domain.model.Result
import com.ralphordanza.budgetup.core.domain.model.Success
import com.ralphordanza.budgetup.core.domain.model.Wallet
import com.ralphordanza.budgetup.core.domain.network.WalletDto
import com.ralphordanza.budgetup.core.domain.network.WalletDtoMapper
import com.ralphordanza.budgetup.framework.extensions.awaitTaskResult
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class WalletDataSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val walletDtoMapper: WalletDtoMapper
) : WalletDataSource {

    override suspend fun getWallets(userId: String): List<Wallet> {
        return walletDtoMapper.fromEntityList(
            firebaseFirestore.collection("users")
                .document(userId)
                .collection("wallets")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()
                .toObjects(WalletDto::class.java)
        )
    }

    override suspend fun getTotal(userId: String): Double {
        var total = 0.0
        walletDtoMapper.fromEntityList(
            firebaseFirestore.collection("users")
                .document(userId)
                .collection("wallets")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()
                .toObjects(WalletDto::class.java)
        ).forEach {
            total += it.amount.toDouble()
        }
        return total
    }

    override suspend fun addWallet(
        userId: String,
        walletName: String,
        initialAmt: String
    ): Result<DocumentReference> {
        return try {
            val wallet = hashMapOf(
                "name" to walletName,
                "amount" to initialAmt,
                "createdAt" to FieldValue.serverTimestamp()
            )
            val docRef = firebaseFirestore.collection("users")
                .document(userId)
                .collection("wallets")
                .add(wallet)
            Success(awaitTaskResult(docRef))
        } catch (e: Exception) {
            Failed(Exception(e.localizedMessage))
        }
    }

    override suspend fun adjustWallet(wallet: Wallet) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteWallet(wallet: Wallet) {
        TODO("Not yet implemented")
    }
}