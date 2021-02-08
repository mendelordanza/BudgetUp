package com.ralphordanza.budgetup.framework.extensions

fun Double.getDecimalString(): String{
    return String.format(
        "%.2f",
        this
    )
}