package com.ralphordanza.budgetup.framework.utils

import android.content.Context
import androidx.datastore.preferences.core.clear
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionManager @Inject constructor(context: Context) {
    private val dataStore = context.createDataStore("session_prefs")

    companion object {
        val USER_ID = preferencesKey<String>("USER_ID")
    }

    suspend fun storeUserId(userId: String){
        dataStore.edit {
            it[USER_ID] = userId
        }
    }

    val userIdFlow: Flow<String> = dataStore.data.map {
        it[USER_ID] ?: ""
    }


    suspend fun clearSession(){
        dataStore.edit {
            it.clear()
        }
    }
}