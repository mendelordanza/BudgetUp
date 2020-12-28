package com.ralphordanza.budgetup.framework.ui.calculator

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphordanza.budgetup.core.interactors.Interactors
import kotlinx.coroutines.launch

class CalculatorViewModel @ViewModelInject constructor(private val interactors: Interactors) : ViewModel() {

    private val result = MutableLiveData<String>()
    fun getResult(): LiveData<String> = result

    fun compute(expression: String) = viewModelScope.launch {
        val ans = if(interactors.calculatorCompute(expression).toString() == "NaN"){
            "0.0"
        }
        else{
            interactors.calculatorCompute(expression).toString()
        }
        result.postValue(ans)
    }
}