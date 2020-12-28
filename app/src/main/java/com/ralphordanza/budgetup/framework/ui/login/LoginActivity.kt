package com.ralphordanza.budgetup.framework.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.ralphordanza.budgetup.R
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

        binding.btnGuest.setOnClickListener {
            loginAsGuest()
        }

        binding.btnRegister.setOnClickListener {
            start<RegisterActivity>()
        }
    }

    private fun loginUser() {
        loginViewModel.login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
    }

    private fun loginAsGuest(){
        loginViewModel.loginAsGuest()
    }

    private fun observeData(){
        loginViewModel.getIsLoading().observe(this, Observer { loading ->
            binding.progressBar.isVisible = loading
            binding.btnLogin.isVisible = !loading
            binding.btnGuest.isVisible = !loading
        })

        loginViewModel.getLoginResult().observe(this, Observer { result ->
            //user.uid
            result.user?.let { user ->
                start<MainActivity>()
                finishAffinity()
            }
        })

        loginViewModel.getMessage().observe(this, Observer { message ->
            toast(message)
        })
    }
}