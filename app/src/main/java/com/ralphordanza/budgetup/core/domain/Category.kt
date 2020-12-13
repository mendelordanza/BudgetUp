package com.ralphordanza.budgetup.core.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    val id: String,
    val name: String
) : Parcelable