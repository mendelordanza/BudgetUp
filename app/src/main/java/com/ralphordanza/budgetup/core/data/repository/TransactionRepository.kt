package com.ralphordanza.budgetup.core.data.repository

import com.ralphordanza.budgetup.core.data.datasource.TransactionDataSource
import com.ralphordanza.budgetup.core.domain.Transaction
import javax.inject.Inject

class TransactionRepository @Inject constructor(private val transactionDataSource: TransactionDataSource) {

    suspend fun addTransaction(transaction: Transaction) = transactionDataSource.addTransaction(transaction)

    suspend fun updateTransaction(transaction: Transaction) = transactionDataSource.updateTransaction(transaction)

    suspend fun deleteTransaction(transaction: Transaction) = transactionDataSource.deleteTransaction(transaction)
}