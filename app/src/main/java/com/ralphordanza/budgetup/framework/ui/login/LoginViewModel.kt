package com.ralphordanza.budgetup.framework.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.ralphordanza.budgetup.core.domain.model.Resource
import com.ralphordanza.budgetup.core.interactors.Interactors
import com.ralphordanza.budgetup.framework.utils.SessionManager
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(
    private val interactors: Interactors,
    private val sessionManager: SessionManager
) :
    ViewModel() {

    private val loginResult = MutableLiveData<Resource<AuthResult>>()
    fun getLoginResult(): LiveData<Resource<AuthResult>> = loginResult

    private val logout = MutableLiveData<Resource<FirebaseUser?>>()
    fun getLogout(): LiveData<Resource<FirebaseUser?>> = logout

    fun login(email: String, password: String) = viewModelScope.launch {
        loginResult.value = Resource.loading(null)
        loginResult.value = interactors.loginUser(email, password)
    }

    fun storeUserId(userId: String) = viewModelScope.launch {
        sessionManager.storeUserId(userId)
    }

    fun logout() = viewModelScope.launch {
        logout.value = Resource.loading(null)
        logout.value = interactors.logoutUser()
    }

    fun clearSession() = viewModelScope.launch {
        sessionManager.clearSession()
    }
}