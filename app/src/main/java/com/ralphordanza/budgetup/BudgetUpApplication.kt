package com.ralphordanza.budgetup

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class BudgetUpApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}