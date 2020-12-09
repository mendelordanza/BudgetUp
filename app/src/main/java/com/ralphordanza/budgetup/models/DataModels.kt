package com.ralphordanza.budgetup.models

import android.os.Parcelable
import com.google.firebase.database.PropertyName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Wallet(
    @PropertyName("id") val id: String,
    @PropertyName("name") val name: String,
    @PropertyName("amount") val amount: String
) : Parcelable {
    constructor() : this("", "", "")
}

@Parcelize
data class Transaction(
    val id: String,
    val date: String,
    val category: String,
    val wallet: String,
    val note: String = ""
) : Parcelable

@Parcelize
data class TransactionSection(
    val id: String,
    val month: String,
    val items: List<Transaction>
) : Parcelable

@Parcelize
data class Category(
    val id: String,
    val name: String
) : Parcelable
