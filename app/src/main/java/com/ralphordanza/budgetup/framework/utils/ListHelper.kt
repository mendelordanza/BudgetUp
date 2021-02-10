package com.ralphordanza.budgetup.framework.utils

import com.ralphordanza.budgetup.core.domain.model.Wallet

object ListHelper{

    fun getWalletFromList(list: List<Wallet>, id: String): Wallet? {
        var wallet: Wallet? = null
        list.forEach {
            if(it.id == id){
                wallet = it
            }
        }
        return wallet
    }
}
