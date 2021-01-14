package com.ralphordanza.budgetup.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Transaction(
    val id: String,
    val date: String,
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