package com.ralphordanza.budgetup.framework.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ralphordanza.budgetup.databinding.ActivityRegisterBinding
import com.ralphordanza.budgetup.framework.ui.MainActivity
import com.ralphordanza.budgetup.framework.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import splitties.activities.start
import splitties.toast.toast

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()

        if (firebaseAuth.currentUser != null) {
            start<MainActivity>()
        }

        attachActions()
        observeData()
    }

    private fun attachActions() {
        binding.btnRegister.setOnClickListener {
            registerViewModel.register(
                binding.etFirstName.text.toString(),
                binding.etLastName.text.toString(),
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }

        binding.btnLogin.setOnClickListener {
            start<LoginActivity>()
        }
    }

    private fun observeData(){
        registerViewModel.getIsSaveSuccess().observe(this, Observer { isSuccess ->
            if(isSuccess){
                start<MainActivity>()
                finish()
            }
        })

        registerViewModel.getMessage().observe(this, Observer { message ->
            toast(message)
        })
    }
}