package com.ralphordanza.budgetup.framework.ui.wallets

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphordanza.budgetup.core.domain.model.Failed
import com.ralphordanza.budgetup.core.domain.model.Success
import com.ralphordanza.budgetup.core.domain.model.Wallet
import com.ralphordanza.budgetup.core.interactors.Interactors
import com.ralphordanza.budgetup.framework.utils.SessionManager
import kotlinx.coroutines.launch

class WalletViewModel @ViewModelInject constructor(
    private val interactors: Interactors,
    private val sessionManager: SessionManager
) :
    ViewModel() {

    var totalAmountHolder: Long = 0

    fun getSessionManager() = sessionManager

    private val wallets = MutableLiveData<List<Wallet>>()
    fun getWallets(): LiveData<List<Wallet>> = wallets

    private val total = MutableLiveData<Int>()
    fun getTotal(): LiveData<Int> = total

    private val isAdded = MutableLiveData<Boolean>()
    fun getIsAdded(): LiveData<Boolean> = isAdded

    private val errorMessage = MutableLiveData<String>()
    fun getErrorMessage(): LiveData<String> = errorMessage

    fun getWallets(userId: String) = viewModelScope.launch {
        wallets.postValue(interactors.getWallets(userId))
    }

    fun addWallet(userId: String, walletName: String, initialAmt: String) = viewModelScope.launch {
        when (val result = interactors.addWallet(userId, walletName, initialAmt)) {
            is Success -> {
                isAdded.value = true
            }
            is Failed -> {
                isAdded.value = false
                errorMessage.value = result.message
            }
        }
    }

    fun getTotal(userId: String) = viewModelScope.launch {
        total.postValue(interactors.getTotal(userId))
    }
}