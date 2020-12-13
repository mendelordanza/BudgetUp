package com.ralphordanza.budgetup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import splitties.activities.start
import splitties.activities.startActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        start<RegisterActivity>()
        finish()
    }
}