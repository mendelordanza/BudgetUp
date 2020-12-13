package com.ralphordanza.budgetup.framework.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ralphordanza.budgetup.databinding.ActivityRegisterBinding
import com.ralphordanza.budgetup.framework.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import splitties.activities.start

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
    }

    private fun attachActions() {
        binding.btnRegister.setOnClickListener {
            //TODO create clean architecture
            registerUser()
        }
    }

    private fun registerUser() {
        registerViewModel.register(
            binding.etFirstName.text.toString(),
            binding.etLastName.text.toString(),
            binding.etEmail.text.toString(),
            binding.etPassword.text.toString()
        )
    }
}