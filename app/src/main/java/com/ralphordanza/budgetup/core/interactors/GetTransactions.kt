package com.ralphordanza.budgetup.core.interactors

import com.ralphordanza.budgetup.core.data.repository.TransactionRepository
import javax.inject.Inject

class GetTransactions @Inject constructor(private val transactionRepository: TransactionRepository) {

    suspend operator fun invoke(userId: String, walletId: String) = transactionRepository.getTransactions(userId, walletId)
}