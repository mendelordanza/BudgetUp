package com.ralphordanza.budgetup.framework.ui.customview

import android.app.Activity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.ralphordanza.budgetup.R

class LoadingDialog(private val activity: Activity) {

    private lateinit var loadingDialog: MaterialDialog

    fun startLoadingDialog() {
        loadingDialog = MaterialDialog(activity)
            .customView(R.layout.dialog_loading)
            .cancelable(false)
        loadingDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        loadingDialog.show()
    }

    fun dismissLoadingDialog(){
        if(::loadingDialog.isInitialized){
            loadingDialog.dismiss()
        }
    }
}