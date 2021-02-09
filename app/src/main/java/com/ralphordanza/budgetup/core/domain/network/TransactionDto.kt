package com.ralphordanza.budgetup.core.domain.network

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class TransactionDto(
    @DocumentId
    val id: String,
    val createdAt: Timestamp,
    val type: String,
    val amount: String,
    val walletId: String,
    val note: String = ""
){
    constructor() : this("", Timestamp.now(), "", "", "", "")
}