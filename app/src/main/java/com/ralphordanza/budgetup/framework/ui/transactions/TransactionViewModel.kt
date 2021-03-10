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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TransactionViewModel @ViewModelInject constructor(
    private val interactors: Interactors,
    private val sessionManager: SessionManager
) : ViewModel() {

    sealed class TransactionEvent{
        class TransactionDeleteEvent(val resource: Resource<String>): TransactionEvent()
    }

    private val eventChannel = Channel<TransactionEvent>()
    val eventFlow = eventChannel.receiveAsFlow()

    private val transactions = MutableLiveData<List<TransactionSection>>()
    fun getTransactions(): LiveData<List<TransactionSection>> = transactions

    private val userId = MutableLiveData<String>()
    fun getUserId(): LiveData<String> = userId

    private val walletId = MutableLiveData<String>()
    fun getWalletId(): LiveData<String> = walletId

    private val isTransactionAdded = MutableLiveData<Resource<String>>()
    fun getIsTransactionAdded(): LiveData<Resource<String>> = isTransactionAdded

    private val totalTransactions = MutableLiveData<Double>()
    fun getTotalTransactions(): LiveData<Double> = totalTransactions

    fun loadTransactions(userId: String, walletId: String) = viewModelScope.launch {
        transactions.postValue(interactors.getTransactions(userId, walletId))
    }

    fun getTotalTransactions(userId: String, walletId: String, initialAmt: Double) = viewModelScope.launch {
        totalTransactions.postValue(interactors.getTotalTransactions(userId, walletId, initialAmt))
    }

    fun userId() = viewModelScope.launch {
        sessionManager.userIdFlow.collect {
            userId.value = it
        }
    }

    fun storeWalletId(walletId: String) = viewModelScope.launch {
        sessionManager.storeWalletId(walletId)
    }

    fun walletId() = viewModelScope.launch {
        sessionManager.walletIdFlow.collect {
            walletId.value = it
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

    fun deleteTransaction(transaction: Transaction) = viewModelScope.launch {
        eventChannel.send(TransactionEvent.TransactionDeleteEvent(interactors.deleteTransaction(transaction)))
    }
}