package com.ralphordanza.budgetup.core.data.datasource

import com.google.firebase.firestore.DocumentReference
import com.ralphordanza.budgetup.core.domain.model.Resource
import com.ralphordanza.budgetup.core.domain.model.Transaction
import com.ralphordanza.budgetup.core.domain.model.TransactionSection

interface TransactionDataSource {

    suspend fun getTransactions(userId: String, walletId: String): List<TransactionSection>

    suspend fun getTotalTransaction(userId: String, walletId: String, initialAmt: Double): Double

    suspend fun addTransaction(
        amount: String,
        userId: String,
        date: String,
        walletId: String,
        type: String,
        note: String
    ): Resource<String>

    suspend fun updateTransaction(transaction: Transaction)

    suspend fun deleteTransaction(transaction: Transaction)

    suspend fun calculate(expression: String): Double
}