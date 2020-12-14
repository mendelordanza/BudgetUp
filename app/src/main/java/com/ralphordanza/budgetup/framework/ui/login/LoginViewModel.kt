package com.ralphordanza.budgetup.framework.ui.login

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.ralphordanza.budgetup.core.domain.Failed
import com.ralphordanza.budgetup.core.domain.Success
import com.ralphordanza.budgetup.core.interactors.Interactors
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(private val interactors: Interactors) :
    ViewModel() {

    private val loginResult = MutableLiveData<AuthResult>()
    fun getLoginResult(): LiveData<AuthResult> = loginResult

    private val message = MutableLiveData<String>()
    fun getMessage(): LiveData<String> = message

    private val isLoading = MutableLiveData<Boolean>()
    fun getIsLoading(): LiveData<Boolean> = isLoading

    fun login(email: String, password: String) = viewModelScope.launch {
        isLoading.value = true
        when(val result = interactors.loginUser(email, password)){
            is Success -> {
                isLoading.value = false
                loginResult.postValue(result.data)
            }
            is Failed -> {
                isLoading.value = false
                message.value = result.message
            }
        }
    }

    fun logout() = viewModelScope.launch {
        interactors.logoutUser
    }
}