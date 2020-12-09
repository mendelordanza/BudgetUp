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