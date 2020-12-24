package com.ralphordanza.budgetup.core.data.implementation

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.ralphordanza.budgetup.core.data.datasource.UserDataSource
import com.ralphordanza.budgetup.core.domain.Failed
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

    override suspend fun register(email: String, password: String): Result<AuthResult> {
        return try{
            val task = firebaseAuth.createUserWithEmailAndPassword(
                email,
                password
            )
            Success(awaitTaskResult(task))
        } catch (e: Exception){
            Failed(Exception(e.localizedMessage))
        }
    }

    override suspend fun saveToFirestore(user: User): Boolean {
        return try{
            firebaseFirestore.collection("users")
                .document(user.id)
                .set(user)
            true
        } catch (e: Exception){
            false
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

    override suspend fun loginAsGuest(): Result<AuthResult>  {
        return try{
            val task = firebaseAuth.signInAnonymously()
            Success(awaitTaskResult(task))
        } catch (e: Exception){
            Failed(Exception(e.localizedMessage))
        }
    }

    override suspend fun logout(): FirebaseUser? {
        firebaseAuth.signOut()
        return firebaseAuth.currentUser
    }
}