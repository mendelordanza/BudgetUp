package com.ralphordanza.budgetup.framework.ui.wallets

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphordanza.budgetup.core.domain.Failed
import com.ralphordanza.budgetup.core.domain.Success
import com.ralphordanza.budgetup.core.domain.Wallet
import com.ralphordanza.budgetup.core.interactors.Interactors
import kotlinx.coroutines.launch

class WalletViewModel @ViewModelInject constructor(private val interactors: Interactors) :
    ViewModel() {

    private val wallets = MutableLiveData<List<Wallet>>()
    fun getWallets(): LiveData<List<Wallet>> = wallets

    private val isAdded = MutableLiveData<Boolean>()
    fun getIsAdded(): LiveData<Boolean> = isAdded

    private val errorMessage = MutableLiveData<String>()
    fun getErrorMessage(): LiveData<String> = errorMessage

    fun getWallets(userId: String) = viewModelScope.launch {
        wallets.postValue(interactors.getWallets(userId))
    }

    fun addWallet(userId: String, walletName: String, initialAmt: String) = viewModelScope.launch {
        when(val result = interactors.addWallet(userId, walletName, initialAmt)){
            is Success -> {
                isAdded.value = true
            }
            is Failed -> {
                isAdded.value = false
                errorMessage.value = result.message
            }
        }
    }
}