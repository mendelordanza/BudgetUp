package com.ralphordanza.budgetup.core.domain.network

import com.google.firebase.firestore.DocumentId

data class TransactionDto(
    @DocumentId
    val id: String,
    val date: String,
    val type: String,
    val amount: String,
    val walletId: String,
    val note: String = ""
){
    constructor() : this("", "", "", "", "", "")
}