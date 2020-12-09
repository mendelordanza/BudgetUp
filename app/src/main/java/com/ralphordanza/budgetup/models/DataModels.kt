package com.ralphordanza.budgetup.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Wallet(
    val id: String,
    val name: String,
    val amount: String
) : Parcelable

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