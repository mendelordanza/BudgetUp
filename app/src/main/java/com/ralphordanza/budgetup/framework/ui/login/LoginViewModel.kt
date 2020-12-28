package com.ralphordanza.budgetup.framework.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.ralphordanza.budgetup.core.domain.model.Failed
import com.ralphordanza.budgetup.core.domain.model.Success
import com.ralphordanza.budgetup.core.interactors.Interactors
import com.ralphordanza.budgetup.framework.utils.SessionManager
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(
    private val interactors: Interactors,
    private val sessionManager: SessionManager
) :
    ViewModel() {

    fun getSessionManager() = sessionManager

    private val loginResult = MutableLiveData<AuthResult>()
    fun getLoginResult(): LiveData<AuthResult> = loginResult

    private val logout = MutableLiveData<Boolean>()
    fun getLogout(): LiveData<Boolean> = logout

    private val message = MutableLiveData<String>()
    fun getMessage(): LiveData<String> = message

    private val isLoading = MutableLiveData<Boolean>()
    fun getIsLoading(): LiveData<Boolean> = isLoading

    fun login(email: String, password: String) = viewModelScope.launch {
        isLoading.value = true
        when (val result = interactors.loginUser(email, password)) {
            is Success -> {
                result.data.user?.let {
                    getSessionManager().storeUserId(it.uid)
                }
                isLoading.value = false
                loginResult.postValue(result.data)
            }
            is Failed -> {
                isLoading.value = false
                message.value = result.message
            }
        }
    }

    fun loginAsGuest() = viewModelScope.launch {
        isLoading.value = true
        when (val result = interactors.loginAsGuest()) {
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
        //if result is null, logout is successful
        logout.value = interactors.logoutUser() == null
        getSessionManager().clearSession()
    }
}