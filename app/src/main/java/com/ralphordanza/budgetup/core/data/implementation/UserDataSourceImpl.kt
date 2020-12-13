package com.ralphordanza.budgetup.core.data.implementation

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.ralphordanza.budgetup.core.data.datasource.UserDataSource
import com.ralphordanza.budgetup.core.domain.User
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) : UserDataSource {

    override suspend fun register(user: User) {
        firebaseAuth.createUserWithEmailAndPassword(
            user.email,
            user.password
        ).addOnCompleteListener { task ->
            //TODO save account to firestore
            Log.d("REGISTER", "success!")
        }
    }

    override suspend fun login(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }
}