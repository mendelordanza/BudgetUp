package com.ralphordanza.budgetup.core.data.repository

import com.ralphordanza.budgetup.core.data.datasource.TransactionDataSource
import com.ralphordanza.budgetup.core.domain.model.Transaction
import javax.inject.Inject

class TransactionRepository @Inject constructor(private val transactionDataSource: TransactionDataSource) {

    suspend fun getTransactions(userId: String, walletId: String) =
        transactionDataSource.getTransactions(userId, walletId)

    suspend fun getTotalTransactions(userId: String, walletId: String, initialAmt: Double) =
        transactionDataSource.getTotalTransaction(userId, walletId, initialAmt)

    suspend fun addTransaction(
        amount: String,
        userId: String,
        date: String,
        walletId: String,
        type: String,
        note: String
    ) =
        transactionDataSource.addTransaction(amount, userId, date, walletId, type, note)

    suspend fun updateTransaction(transaction: Transaction) =
        transactionDataSource.updateTransaction(transaction)

    suspend fun deleteTransaction(transaction: Transaction) =
        transactionDataSource.deleteTransaction(transaction)

    suspend fun calculate(expression: String) = transactionDataSource.calculate(expression)
}