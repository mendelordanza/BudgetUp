package com.ralphordanza.budgetup.core.interactors

import com.ralphordanza.budgetup.core.data.repository.TransactionRepository
import com.ralphordanza.budgetup.core.domain.model.Transaction
import javax.inject.Inject

class DeleteTransaction @Inject constructor(private val transactionRepository: TransactionRepository) {

    suspend operator fun invoke(transaction: Transaction) = transactionRepository.deleteTransaction(transaction)
}