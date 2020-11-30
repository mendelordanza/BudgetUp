package com.ralphordanza.budgetup.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Wallet(
    val id: String,
    val name: String,
    val amount: String
) : Parcelable