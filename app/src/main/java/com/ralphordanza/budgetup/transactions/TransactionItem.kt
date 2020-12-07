package com.ralphordanza.budgetup.transactions

import android.view.View
import com.ralphordanza.budgetup.R
import com.ralphordanza.budgetup.databinding.ItemTransactionBinding
import com.ralphordanza.budgetup.models.Transaction
import com.xwray.groupie.viewbinding.BindableItem

class TransactionItem(private val transaction: Transaction) : BindableItem<ItemTransactionBinding>() {

    override fun initializeViewBinding(view: View): ItemTransactionBinding {
        return ItemTransactionBinding.bind(view)
    }

    override fun getLayout() = R.layout.item_transaction

    override fun bind(binding: ItemTransactionBinding, position: Int) {
        binding.txtDay.text = transaction.date //Convert to day format
    }
}