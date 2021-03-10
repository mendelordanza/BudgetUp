package com.ralphordanza.budgetup.framework.utils

interface SnackbarListener {

    fun onTransactionChange(message: String)

    fun onWalletChange(message: String)
}