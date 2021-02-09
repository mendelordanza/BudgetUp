package com.ralphordanza.budgetup.core.interactors

import com.ralphordanza.budgetup.core.data.repository.TransactionRepository
import javax.inject.Inject

class AddTransaction @Inject constructor(private val transactionRepo: TransactionRepository) {

    suspend operator fun invoke(
        amount: String,
        userId: String,
        date: String,
        walletId: String,
        type: String,
        note: String
    ) = transactionRepo.addTransaction(amount, userId, date, walletId, type, note)
}