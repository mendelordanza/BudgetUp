package com.ralphordanza.budgetup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ralphordanza.budgetup.databinding.ActivityMainBinding
import splitties.toast.toast

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        attachActions()
    }

    private fun attachActions(){
        binding.bottomAppBar.setNavigationOnClickListener {
            toast("Navigation")
        }
    }

}