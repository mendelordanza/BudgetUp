package com.ralphordanza.budgetup.core.data.implementation

import com.google.firebase.firestore.*
import com.ralphordanza.budgetup.core.data.datasource.WalletDataSource
import com.ralphordanza.budgetup.core.domain.Failed
import com.ralphordanza.budgetup.core.domain.Result
import com.ralphordanza.budgetup.core.domain.Success
import com.ralphordanza.budgetup.core.domain.Wallet
import com.ralphordanza.budgetup.framework.extensions.awaitTaskResult
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class WalletDataSourceImpl @Inject constructor(private val firebaseFirestore: FirebaseFirestore) : WalletDataSource {

    override suspend fun getWallets(userId: String): List<Wallet> {
        return firebaseFirestore.collection("users")
            .document(userId)
            .collection("wallets")
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .get()
            .await()
            .toObjects(Wallet::class.java)
    }

    override suspend fun addWallet(userId: String, walletName: String, initialAmt: String): Result<DocumentReference> {
        return try{
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
        } catch (e: Exception){
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