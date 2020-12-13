package com.ralphordanza.budgetup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ralphordanza.budgetup.databinding.ActivityRegisterBinding
import splitties.activities.start

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore

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
        firebaseAuth.createUserWithEmailAndPassword(
            binding.etEmail.text.toString(),
            binding.etPassword.text.toString()
        ).addOnCompleteListener { task ->
            //TODO save account to firestore
        }
    }
}