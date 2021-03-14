package com.ralphordanza.budgetup.core.data.implementation

import android.util.Log
import com.google.firebase.firestore.*
import com.ralphordanza.budgetup.core.data.datasource.WalletDataSource
import com.ralphordanza.budgetup.core.domain.model.*
import com.ralphordanza.budgetup.core.domain.model.Resource.Companion.DEFAULT_ERROR_MESSAGE
import com.ralphordanza.budgetup.core.domain.network.WalletDto
import com.ralphordanza.budgetup.core.domain.network.WalletDtoMapper
import com.ralphordanza.budgetup.framework.utils.Constants.EXPENSE
import com.ralphordanza.budgetup.framework.utils.Constants.INCOME
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
    ): Resource<String> {
        return try {
            val wallet = hashMapOf(
                "name" to walletName,
                "amount" to initialAmt,
                "createdAt" to FieldValue.serverTimestamp()
            )
            firebaseFirestore.collection("users")
                .document(userId)
                .collection("wallets")
                .add(wallet)

            val walletData = firebaseFirestore.collection("users")
                .document(userId)
                .collection("wallets")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()
                .toObjects(WalletDto::class.java)[0]

            val transaction = hashMapOf(
                "amount" to walletData.amount,
                "createdAt" to FieldValue.serverTimestamp(),
                "note" to "Initial Amount",
                "type" to INCOME,
                "userId" to userId,
                "walletId" to  walletData.id
            )
            firebaseFirestore.collection("transactions")
                .add(transaction)

            Resource.success("Wallet created!")
        } catch (e: Exception) {
            Resource.error(e.localizedMessage ?: DEFAULT_ERROR_MESSAGE, null)
        }
    }

    override suspend fun adjustWallet(wallet: Wallet) {
        TODO("Not yet implemented")
    }

    override suspend fun updateWalletAmount(updatedAmt: String, walletId: String, userId: String): Resource<String> {
        return try {
            firebaseFirestore.collection("users")
                .document(userId)
                .collection("wallets")
                .document(walletId)
                .update("amount", updatedAmt)
            Resource.success("Wallet amount updated!")
        } catch (e: Exception) {
            Resource.error(e.localizedMessage ?: DEFAULT_ERROR_MESSAGE, null)
        }
    }

    override suspend fun updateWallet(
        updatedAmt: String,
        updatedName: String,
        walletId: String,
        userId: String
    ): Resource<String> {
        return try {
            val walletData = firebaseFirestore.collection("users")
                .document(userId)
                .collection("wallets")
                .document(walletId)
                .get()
                .await()
            val prevAmt = walletData.get("amount") as String

            var type = ""
            var diff = 0.0
            val finalAmt = when {
                prevAmt.toDouble() > updatedAmt.toDouble() -> {
                    type = EXPENSE
                    diff = prevAmt.toDouble() - updatedAmt.toDouble()
                    //THEN SUBTRACT THE DIFF TO PREVIOUS AMOUNT
                    prevAmt.toDouble() - diff
                }
                prevAmt.toDouble() < updatedAmt.toDouble() -> {
                    type = INCOME
                    diff = updatedAmt.toDouble() - prevAmt.toDouble()
                    //THEN ADD THE DIFF TO PREVIOUS AMOUNT
                    prevAmt.toDouble() + diff
                }
                else -> {
                    prevAmt.toDouble()
                }
            }

            //ADD NEW TRANSACTION (ADJUSTED BALANCE)
            val transaction = hashMapOf(
                "amount" to diff.toString(),
                "createdAt" to FieldValue.serverTimestamp(),
                "note" to "Adjusted Balance",
                "type" to type,
                "userId" to userId,
                "walletId" to  walletData.id
            )
            firebaseFirestore.collection("transactions")
                .add(transaction)

            //UPDATE WALLET
            val wallet = hashMapOf(
                "name" to updatedName,
                "amount" to updatedAmt,
                "createdAt" to FieldValue.serverTimestamp()
            )
            firebaseFirestore.collection("users")
                .document(userId)
                .collection("wallets")
                .document(walletId)
                .set(wallet)
            Resource.success("Wallet updated")
        } catch (e: Exception) {
            Resource.error(e.localizedMessage ?: DEFAULT_ERROR_MESSAGE, null)
        }
    }

    override suspend fun deleteWallet(userId: String, wallet: Wallet): Resource<String> {
        return try {
            firebaseFirestore.collection("users")
                .document(userId)
                .collection("wallets")
                .document(wallet.id)
                .delete()
            Resource.success("${wallet.name} deleted!")
        } catch (e: Exception) {
            Resource.error(e.localizedMessage ?: DEFAULT_ERROR_MESSAGE, null)
        }
    }
}