package com.ralphordanza.budgetup.core.data.datasource

import com.ralphordanza.budgetup.core.domain.Transaction

interface TransactionDataSource {

    suspend fun addTransaction(transaction: Transaction)

    suspend fun updateTransaction(transaction: Transaction)

    suspend fun deleteTransaction(transaction: Transaction)
}