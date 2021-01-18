package com.ralphordanza.budgetup.core.data.implementation

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.ralphordanza.budgetup.core.data.datasource.TransactionDataSource
import com.ralphordanza.budgetup.core.domain.model.*
import com.ralphordanza.budgetup.core.domain.network.TransactionDto
import com.ralphordanza.budgetup.core.domain.network.TransactionDtoMapper
import com.ralphordanza.budgetup.core.domain.network.WalletDto
import com.ralphordanza.budgetup.framework.extensions.awaitTaskResult
import com.ralphordanza.budgetup.framework.utils.DateHelper
import kotlinx.coroutines.tasks.await
import org.mariuszgromada.math.mxparser.*
import java.lang.Exception
import javax.inject.Inject

class TransactionDataSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val transactionDtoMapper: TransactionDtoMapper
) : TransactionDataSource {

    override suspend fun getTransactions(userId: String): List<TransactionSection> {
        val months = listOf(
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "May",
            "Jun",
            "Jul",
            "Aug",
            "Sep",
            "Oct",
            "Nov",
            "Dec"
        )

        val transactionsList = transactionDtoMapper.fromEntityList(
            firebaseFirestore.collection("transactions")
                .whereEqualTo("userId", userId)
                .get()
                .await()
                .toObjects(TransactionDto::class.java)
        )

        val transactionSections = months.map { month ->
            val filteredList = transactionsList.filter { trans ->
                DateHelper.parseDate(
                    "EEE MMM dd HH:mm:ss zzzz yyyy",
                    "MMM",
                    trans.createdAt.toDate().toString()) == month
            }
            TransactionSection(Long.MIN_VALUE.toString(), month, filteredList)
        }

        return transactionSections
    }

    override suspend fun addTransaction(
        amount: String,
        userId: String,
        walletId: String,
        type: String,
        note: String
    ): Result<DocumentReference> {
        return try {
            val transaction = hashMapOf(
                "amount" to amount,
                "createdAt" to FieldValue.serverTimestamp(),
                "note" to note,
                "type" to type,
                "userId" to userId,
                "walletId" to walletId
            )
            val docRef = firebaseFirestore.collection("transactions")
                .add(transaction)
            Success(awaitTaskResult(docRef))
        } catch (e: Exception) {
            Failed(Exception(e.localizedMessage))
        }
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        TODO("Not yet implemented")
    }

    override suspend fun calculate(expression: String): Double {
        var finalExp = expression
        finalExp = finalExp.replace("÷", "/")
        finalExp = finalExp.replace("×", "*")

        val exp = Expression(finalExp)
        return exp.calculate()
    }
}