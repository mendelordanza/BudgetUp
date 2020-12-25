package com.ralphordanza.budgetup.core.domain.model

import com.google.firebase.firestore.DocumentId

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)