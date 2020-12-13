package com.ralphordanza.budgetup.framework.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ralphordanza.budgetup.framework.ui.register.RegisterActivity
import splitties.activities.start

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        start<RegisterActivity>()
        finish()
    }
}