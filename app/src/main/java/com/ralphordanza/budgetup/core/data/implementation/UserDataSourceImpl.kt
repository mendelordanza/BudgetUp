package com.ralphordanza.budgetup.core.data.implementation

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.ralphordanza.budgetup.core.data.datasource.UserDataSource
import com.ralphordanza.budgetup.core.domain.Failed
import com.ralphordanza.budgetup.core.domain.Result
import com.ralphordanza.budgetup.core.domain.Success
import com.ralphordanza.budgetup.core.domain.User
import com.ralphordanza.budgetup.framework.extensions.awaitTaskCompletable
import com.ralphordanza.budgetup.framework.extensions.awaitTaskResult
import java.lang.Exception
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : UserDataSource {

    override suspend fun register(user: User): Result<AuthResult> {
        return try{
            val task = firebaseAuth.createUserWithEmailAndPassword(
                user.email,
                user.password
            )
            Success(awaitTaskResult(task))
        } catch (e: Exception){
            Failed(Exception(e.localizedMessage))
        }
    }

    override suspend fun saveToFirestore(user: User): Result<DocumentReference> {
        return try{
            val docRef = firebaseFirestore.collection("users")
                .add(user)
            Success(awaitTaskResult(docRef))
        } catch (e: Exception){
            Failed(Exception(e.localizedMessage))
        }
    }

    override suspend fun login(email: String, password: String): Result<AuthResult> {
        return try{
            val task = firebaseAuth.signInWithEmailAndPassword(email, password)
            Success(awaitTaskResult(task))
        } catch (e: Exception){
            Failed(Exception(e.localizedMessage))
        }
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }
}