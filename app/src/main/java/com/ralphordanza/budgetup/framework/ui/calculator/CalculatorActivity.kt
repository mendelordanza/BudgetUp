package com.ralphordanza.budgetup.framework.ui.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ralphordanza.budgetup.R
import com.ralphordanza.budgetup.databinding.ActivityCalculatorBinding

class CalculatorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}