package com.ralphordanza.budgetup.core.data.datasource

import com.google.firebase.firestore.DocumentReference
import com.ralphordanza.budgetup.core.domain.model.Result
import com.ralphordanza.budgetup.core.domain.model.Transaction
import com.ralphordanza.budgetup.core.domain.model.TransactionSection

interface TransactionDataSource {

    suspend fun getTransactions(userId: String): List<TransactionSection>

    suspend fun addTransaction(
        amount: String,
        userId: String,
        walletId: String,
        type: String,
        note: String
    ): Result<DocumentReference>

    suspend fun updateTransaction(transaction: Transaction)

    suspend fun deleteTransaction(transaction: Transaction)

    suspend fun calculate(expression: String): Double
}