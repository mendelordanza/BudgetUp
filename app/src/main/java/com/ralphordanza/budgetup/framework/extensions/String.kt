package com.ralphordanza.budgetup.framework.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun String.snackbar(view: View, anchorView: View) {
    Snackbar.make(
        view,
        this,
        Snackbar.LENGTH_LONG
    ).setAnchorView(anchorView)
        .show()

}

fun String.snackbar(view: View) {
    Snackbar.make(
        view,
        this,
        Snackbar.LENGTH_SHORT
    ).show()
}