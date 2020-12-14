package com.ralphordanza.budgetup.framework.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.ralphordanza.budgetup.R
import com.ralphordanza.budgetup.databinding.ActivityLoginBinding
import com.ralphordanza.budgetup.databinding.ActivityRegisterBinding
import com.ralphordanza.budgetup.framework.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import splitties.activities.start
import splitties.toast.toast

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        attachActions()
        observeData()
    }

    private fun attachActions() {
        binding.btnLogin.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        loginViewModel.login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
    }

    private fun observeData(){
        loginViewModel.getIsLoading().observe(this, Observer { loading ->
            binding.progressBar.isVisible = loading
        })

        loginViewModel.getLoginResult().observe(this, Observer { result ->
            //user.uid
            result.user?.let { user ->
                start<MainActivity>()
                finish()
            }
        })

        loginViewModel.getMessage().observe(this, Observer { message ->
            toast(message)
        })
    }
}