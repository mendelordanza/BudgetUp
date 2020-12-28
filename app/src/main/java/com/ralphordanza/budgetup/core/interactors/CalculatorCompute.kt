package com.ralphordanza.budgetup.core.interactors

import com.ralphordanza.budgetup.core.data.repository.TransactionRepository
import javax.inject.Inject

class CalculatorCompute @Inject constructor(private val transactionRepository: TransactionRepository) {

    suspend operator fun invoke(expression: String): Double {
        return if(expression.isEmpty()){
            0.0
        }
        else{
            transactionRepository.calculate(expression)
        }
    }
}