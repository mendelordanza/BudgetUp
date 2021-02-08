package com.ralphordanza.budgetup.core.data.implementation

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.ralphordanza.budgetup.core.data.datasource.UserDataSource
import com.ralphordanza.budgetup.core.domain.model.*
import com.ralphordanza.budgetup.core.domain.model.Resource.Companion.DEFAULT_ERROR_MESSAGE
import com.ralphordanza.budgetup.framework.extensions.awaitTaskResult
import java.lang.Exception
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : UserDataSource {

    override suspend fun register(email: String, password: String): Resource<AuthResult> {
        return try{
            val task = firebaseAuth.createUserWithEmailAndPassword(
                email,
                password
            )
            Resource.success(awaitTaskResult(task))
        } catch (e: Exception){
            Resource.error(e.localizedMessage ?: DEFAULT_ERROR_MESSAGE, null)
        }
    }

    override suspend fun saveToFirestore(user: User): Resource<Boolean> {
        return try{
            firebaseFirestore.collection("users")
                .document(user.id)
                .set(user)
            Resource.success(true)
        } catch (e: Exception){
            Resource.error(e.localizedMessage ?: DEFAULT_ERROR_MESSAGE, null)
        }
    }

    override suspend fun login(email: String, password: String): Resource<AuthResult> {
        return try{
            val task = firebaseAuth.signInWithEmailAndPassword(email, password)
            Resource.success(awaitTaskResult(task))
        } catch (e: Exception){
            Resource.error(e.localizedMessage ?: DEFAULT_ERROR_MESSAGE, null)
        }
    }

    override suspend fun loginAsGuest(): Resource<AuthResult> {
        return try{
            val task = firebaseAuth.signInAnonymously()
            Resource.success(awaitTaskResult(task))
        } catch (e: Exception){
            Resource.error(e.localizedMessage ?: DEFAULT_ERROR_MESSAGE, null)
        }
    }

    override suspend fun logout(): Resource<FirebaseUser?> {
        return try{
            firebaseAuth.signOut()
            Resource.success(firebaseAuth.currentUser)
        } catch (e: Exception){
            Resource.error(e.localizedMessage ?: DEFAULT_ERROR_MESSAGE, null)
        }
    }
}