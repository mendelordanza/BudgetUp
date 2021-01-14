package com.ralphordanza.budgetup.core.data.repository

import com.ralphordanza.budgetup.core.data.datasource.TransactionDataSource
import com.ralphordanza.budgetup.core.domain.model.Transaction
import javax.inject.Inject

class TransactionRepository @Inject constructor(private val transactionDataSource: TransactionDataSource) {

    suspend fun getTransactions(userId: String) = transactionDataSource.getTransactions(userId)

    suspend fun addTransaction(amount: String,
                               userId: String,
                               walletId: String,
                               type: String,
                               note: String) =
        transactionDataSource.addTransaction(amount, userId, walletId, type, note)

    suspend fun updateTransaction(transaction: Transaction) =
        transactionDataSource.updateTransaction(transaction)

    suspend fun deleteTransaction(transaction: Transaction) =
        transactionDataSource.deleteTransaction(transaction)

    suspend fun calculate(expression: String) = transactionDataSource.calculate(expression)
}