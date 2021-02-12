package com.ralphordanza.budgetup.core.interactors

import com.google.firebase.firestore.DocumentReference
import com.ralphordanza.budgetup.core.data.repository.TransactionRepository
import com.ralphordanza.budgetup.core.domain.model.Resource
import com.ralphordanza.budgetup.framework.utils.Constants.AMOUNT
import com.ralphordanza.budgetup.framework.utils.Constants.DATE
import javax.inject.Inject

class AddTransaction @Inject constructor(private val transactionRepo: TransactionRepository) {

    suspend operator fun invoke(
        amount: String,
        userId: String,
        date: String,
        walletId: String,
        type: String,
        note: String
    ): Resource<String> {

        if(amount.isEmpty()){
            return Resource.error("Amount is required", AMOUNT)
        }

        if(date.isEmpty()){
            return Resource.error("Date is required", DATE)
        }

        return transactionRepo.addTransaction(amount, userId, date, walletId, type, note)
    }
}