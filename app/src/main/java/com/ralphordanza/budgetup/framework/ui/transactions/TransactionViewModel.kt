package com.ralphordanza.budgetup.framework.ui.transactions

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphordanza.budgetup.core.domain.model.Failed
import com.ralphordanza.budgetup.core.domain.model.Success
import com.ralphordanza.budgetup.core.domain.model.Transaction
import com.ralphordanza.budgetup.core.domain.model.TransactionSection
import com.ralphordanza.budgetup.core.interactors.Interactors
import com.ralphordanza.budgetup.framework.utils.SessionManager
import kotlinx.coroutines.launch

class TransactionViewModel @ViewModelInject constructor(
    private val interactors: Interactors,
    private val sessionManager: SessionManager
) : ViewModel() {

    fun getSessionManager() = sessionManager

    private val transactions = MutableLiveData<List<TransactionSection>>()
    fun getTransactions(): LiveData<List<TransactionSection>> = transactions

    private val isTransactionAdded = MutableLiveData<Boolean>()
    fun getIsTransactionAdded(): LiveData<Boolean> = isTransactionAdded

    private val errorMessage = MutableLiveData<String>()
    fun getErrorMessage(): LiveData<String> = errorMessage

    fun loadTransactions(userId: String) = viewModelScope.launch {
        transactions.postValue(interactors.getTransactions(userId))
    }

    fun addTransaction(
        amount: String,
        userId: String,
        walletId: String,
        type: String,
        note: String
    ) = viewModelScope.launch {
        when (val result = interactors.addTransaction(amount, userId, walletId, type, note)) {
            is Success -> {
                isTransactionAdded.value = true
            }
            is Failed -> {
                isTransactionAdded.value = false
                errorMessage.value = result.message
            }
        }
    }
}