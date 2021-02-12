package com.ralphordanza.budgetup.framework.ui.wallets

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentReference
import com.ralphordanza.budgetup.core.domain.model.Resource
import com.ralphordanza.budgetup.core.domain.model.Wallet
import com.ralphordanza.budgetup.core.interactors.Interactors
import com.ralphordanza.budgetup.framework.utils.SessionManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class WalletViewModel @ViewModelInject constructor(
    private val interactors: Interactors,
    private val sessionManager: SessionManager
) :
    ViewModel() {

    fun getSessionManager() = sessionManager

    private val userId = MutableLiveData<String>()
    fun getUserId(): LiveData<String> = userId

    private val wallets = MutableLiveData<List<Wallet>>()
    fun getWallets(): LiveData<List<Wallet>> = wallets

    private val total = MutableLiveData<Double>()
    fun getTotal(): LiveData<Double> = total

    private val isAdded = MutableLiveData<Resource<String>>()
    fun getIsAdded(): LiveData<Resource<String>> = isAdded

    fun userId() = viewModelScope.launch {
        sessionManager.userIdFlow.collect {
            userId.value = it
        }
    }

    fun getWallets(userId: String) = viewModelScope.launch {
        wallets.postValue(interactors.getWallets(userId))
    }

    fun getTotal(userId: String) = viewModelScope.launch {
        total.postValue(interactors.getTotal(userId))
    }

    fun addWallet(userId: String, walletName: String, initialAmt: String) = viewModelScope.launch {
        isAdded.value = Resource.loading(null)
        isAdded.value = interactors.addWallet(userId, walletName, initialAmt)
    }

    fun deleteWallet(userId: String, wallet: Wallet) = viewModelScope.launch{
        interactors.deleteWallet(userId, wallet)
    }
}