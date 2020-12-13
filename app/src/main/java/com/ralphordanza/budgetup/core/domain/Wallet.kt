package com.ralphordanza.budgetup.core.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Wallet(
    val id: String,
    val name: String,
    val amount: String
) : Parcelable {
    constructor() : this("", "", "")
}