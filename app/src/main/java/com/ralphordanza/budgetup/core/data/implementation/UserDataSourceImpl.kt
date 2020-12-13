package com.ralphordanza.budgetup.core.data.implementation

import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ralphordanza.budgetup.core.data.datasource.UserDataSource
import com.ralphordanza.budgetup.core.domain.Error
import com.ralphordanza.budgetup.core.domain.Result
import com.ralphordanza.budgetup.core.domain.Success
import com.ralphordanza.budgetup.core.domain.User
import com.ralphordanza.budgetup.framework.extensions.awaitTaskResult
import java.lang.Exception
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : UserDataSource {

    override suspend fun register(user: User) {
        firebaseAuth.createUserWithEmailAndPassword(
            user.email,
            user.password
        ).addOnCompleteListener { task ->
            firebaseFirestore.collection("users")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Log.d("FIRESTORE", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("FIRESTORE", "Error adding document", e)
                }
        }
    }

    override suspend fun login(email: String, password: String): Result<AuthResult> {
        return try{
            val task = firebaseAuth.signInWithEmailAndPassword(email, password)
            Success(awaitTaskResult(task))
        } catch (e: Exception){
            Error(Exception(e.localizedMessage))
        }
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }
}