package com.ralphordanza.budgetup.core.domain.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Wallet(
    val id: String,
    val name: String,
    val amount: String
) : Parcelable {
    constructor() : this("", "", "")
}