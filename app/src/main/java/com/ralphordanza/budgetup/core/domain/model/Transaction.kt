package com.ralphordanza.budgetup.core.domain.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Transaction(
    val id: String,
    val createdAt: Timestamp,
    val type: String,
    val amount: String,
    val walletId: String,
    val note: String = ""
) : Parcelable

@Parcelize
data class TransactionSection(
    val id: String,
    val month: String,
    val items: List<Transaction>
) : Parcelable