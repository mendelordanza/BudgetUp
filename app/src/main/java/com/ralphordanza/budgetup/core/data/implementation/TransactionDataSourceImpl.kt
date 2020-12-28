package com.ralphordanza.budgetup.core.data.implementation

import android.util.Log
import com.ralphordanza.budgetup.core.data.datasource.TransactionDataSource
import com.ralphordanza.budgetup.core.domain.model.Transaction
import org.mariuszgromada.math.mxparser.*

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

    override suspend fun calculate(expression: String): Double {
        var finalExp = expression
        finalExp = finalExp.replace("÷", "/")
        finalExp = finalExp.replace("×", "*")

        val exp = Expression(finalExp)
        return exp.calculate()
    }
}