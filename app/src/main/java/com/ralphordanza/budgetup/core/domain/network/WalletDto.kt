package com.ralphordanza.budgetup.core.domain.network

import com.google.firebase.firestore.DocumentId

data class WalletDto(
    @DocumentId
    val id: String,
    val name: String,
    val amount: String
) {
    constructor() : this("", "", "")
}