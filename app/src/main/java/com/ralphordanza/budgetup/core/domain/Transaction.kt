package com.ralphordanza.budgetup.core.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Transaction(
    val id: String,
    val date: String,
    val category: String,
    val type: String,
    val amount: Double,
    val wallet: String,
    val note: String = ""
) : Parcelable

@Parcelize
data class TransactionSection(
    val id: String,
    val month: String,
    val items: List<Transaction>
) : Parcelable