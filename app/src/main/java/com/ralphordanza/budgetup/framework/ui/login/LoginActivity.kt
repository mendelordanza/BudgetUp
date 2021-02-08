package com.ralphordanza.budgetup.framework.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.ralphordanza.budgetup.R
import com.ralphordanza.budgetup.core.domain.model.Status
import com.ralphordanza.budgetup.databinding.ActivityLoginBinding
import com.ralphordanza.budgetup.databinding.ActivityRegisterBinding
import com.ralphordanza.budgetup.framework.ui.MainActivity
import com.ralphordanza.budgetup.framework.ui.register.RegisterActivity
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

        binding.imgLogo
    }

    private fun attachActions() {
        binding.btnLogin.setOnClickListener {
            loginUser()
        }

        binding.btnRegister.setOnClickListener {
            start<RegisterActivity>()
        }
    }

    private fun loginUser() {
        loginViewModel.login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
    }

    private fun observeData(){
        loginViewModel.getLoginResult().observe(this, Observer { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    binding.progressBar.isVisible = true
                    binding.btnLogin.isVisible = false
                }
                Status.SUCCESS -> {
                    binding.progressBar.isVisible = false
                    binding.btnLogin.isVisible = true
                    resource.data?.let {
                        loginViewModel.storeUserId(it.user?.uid ?: "")
                        start<MainActivity>()
                        finishAffinity()
                    }
                }
                Status.ERROR -> {
                    binding.progressBar.isVisible = false
                    binding.btnLogin.isVisible = true
                    toast(resource.message.toString())
                }
            }
        })
    }
}