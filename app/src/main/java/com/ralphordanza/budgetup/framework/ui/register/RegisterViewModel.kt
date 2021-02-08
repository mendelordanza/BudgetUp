package com.ralphordanza.budgetup.framework.ui.register

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.ralphordanza.budgetup.core.domain.model.*
import com.ralphordanza.budgetup.core.interactors.Interactors
import kotlinx.coroutines.launch

class RegisterViewModel @ViewModelInject constructor(private val interactors: Interactors) :
    ViewModel() {

    private val isSaveSuccess = MutableLiveData<Resource<Boolean>>()
    fun getIsSaveSuccess(): LiveData<Resource<Boolean>> = isSaveSuccess

    private val isRegistered = MutableLiveData<Resource<AuthResult>>()
    fun getIsRegistered(): LiveData<Resource<AuthResult>> = isRegistered

    private val message = MutableLiveData<String>()
    fun getMessage(): LiveData<String> = message

    fun register(email: String, password: String) =
        viewModelScope.launch {
            isRegistered.value = Resource.loading(null)
            isRegistered.value = interactors.registerUser(email, password)
        }

    fun saveToFirestore(
        userId: String,
        firstName: String, lastName: String,
        email: String, password: String
    ) = viewModelScope.launch {
        val user = User(
            userId,
            firstName,
            lastName,
            email,
            password
        )
        isSaveSuccess.value = interactors.saveToFirestore(user)
    }
}