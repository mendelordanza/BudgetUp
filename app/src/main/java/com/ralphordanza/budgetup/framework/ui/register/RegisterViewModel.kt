package com.ralphordanza.budgetup.framework.ui.register

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphordanza.budgetup.core.domain.Failed
import com.ralphordanza.budgetup.core.domain.Success
import com.ralphordanza.budgetup.core.domain.User
import com.ralphordanza.budgetup.core.interactors.Interactors
import kotlinx.coroutines.launch

class RegisterViewModel @ViewModelInject constructor(private val interactors: Interactors) : ViewModel() {

    private val isSaveSuccess = MutableLiveData<Boolean>()
    fun getIsSaveSuccess(): LiveData<Boolean> = isSaveSuccess

    private val message = MutableLiveData<String>()
    fun getMessage(): LiveData<String> = message

    fun register(firstName: String, lastName: String, email: String, password: String) = viewModelScope.launch {
        val user = User(
            firstName = firstName,
            lastName = lastName,
            email = email,
            password = password
        )
        when(val result = interactors.registerUser(user)){
            is Success -> {
                saveToFirestore(user)
            }
            is Failed -> {
                message.value = result.message
            }
        }
    }

    private fun saveToFirestore(user: User) = viewModelScope.launch {
        when(val result = interactors.saveToFirestore(user)){
            is Success -> {
                isSaveSuccess.value = true
            }
            is Failed -> {
                isSaveSuccess.value = false
                message.value = result.message
            }
        }
    }
}