package com.ralphordanza.budgetup.framework.ui.register

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphordanza.budgetup.core.domain.User
import com.ralphordanza.budgetup.core.interactors.Interactors
import kotlinx.coroutines.launch

class RegisterViewModel @ViewModelInject constructor(private val interactors: Interactors) : ViewModel() {

    fun register(firstName: String, lastName: String, email: String, password: String) = viewModelScope.launch {
        val user = User(
            firstName = firstName,
            lastName = lastName,
            email = email,
            password = password
        )
        interactors.registerUser(user)
    }
}