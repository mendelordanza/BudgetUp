package com.ralphordanza.budgetup.framework.ui.transactions

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentReference
import com.ralphordanza.budgetup.core.domain.model.*
import com.ralphordanza.budgetup.core.interactors.Interactors
import com.ralphordanza.budgetup.framework.utils.SessionManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TransactionViewModel @ViewModelInject constructor(
    private val interactors: Interactors,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val transactions = MutableLiveData<List<TransactionSection>>()
    fun getTransactions(): LiveData<List<TransactionSection>> = transactions

    private val userId = MutableLiveData<String>()
    fun getUserId(): LiveData<String> = userId

    private val isTransactionAdded = MutableLiveData<Resource<DocumentReference>>()
    fun getIsTransactionAdded(): LiveData<Resource<DocumentReference>> = isTransactionAdded

    fun loadTransactions(userId: String, walletId: String) = viewModelScope.launch {
        transactions.postValue(interactors.getTransactions(userId, walletId))
    }

    fun userId() = viewModelScope.launch {
        sessionManager.userIdFlow.collect {
            userId.value = it
        }
    }

    fun addTransaction(
        amount: String,
        userId: String,
        date: String,
        walletId: String,
        type: String,
        note: String
    ) = viewModelScope.launch {
        isTransactionAdded.value = Resource.loading(null)
        isTransactionAdded.value = interactors.addTransaction(amount, userId, date, walletId, type, note)
    }
}