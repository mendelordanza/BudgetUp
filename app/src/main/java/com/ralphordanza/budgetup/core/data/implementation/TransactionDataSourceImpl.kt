package com.ralphordanza.budgetup.core.data.implementation

import com.ralphordanza.budgetup.core.data.datasource.TransactionDataSource
import com.ralphordanza.budgetup.core.domain.model.Transaction

class TransactionDataSourceImpl : TransactionDataSource {

    override suspend fun addTransaction(transaction: Transaction) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        TODO("Not yet implemented")
    }
}