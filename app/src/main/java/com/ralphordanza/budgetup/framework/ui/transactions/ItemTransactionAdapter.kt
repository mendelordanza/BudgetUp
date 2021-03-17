package com.ralphordanza.budgetup.framework.ui.transactions

import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ralphordanza.budgetup.R
import com.ralphordanza.budgetup.databinding.ItemTransactionBinding
import com.ralphordanza.budgetup.core.domain.model.Transaction
import com.ralphordanza.budgetup.core.domain.model.Wallet
import com.ralphordanza.budgetup.framework.extensions.getDecimalString
import com.ralphordanza.budgetup.framework.utils.Constants.EXPENSE
import com.ralphordanza.budgetup.framework.utils.DateHelper

class ItemTransactionAdapter(
    private val onItemClick: (transaction: Transaction) -> Unit,
    private val onMenuClick: (Int, Transaction) -> Unit
) :
    ListAdapter<Transaction, ItemTransactionAdapter.ViewHolder>(DiffCallback()) {
    private lateinit var binding: ItemTransactionBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemTransactionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transactionItem = getItem(position) as Transaction
        holder.bind(transactionItem)
    }

    inner class ViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener {
        fun bind(transaction: Transaction) {
            var amount = ""
            if (transaction.type == EXPENSE) {
                amount = "-${transaction.amount.toDouble().getDecimalString()}"
                binding.txtAmount.setTextColor(ContextCompat.getColor(binding.root.context, R.color.red_400))
            } else {
                amount = "+${transaction.amount.toDouble().getDecimalString()}"
                binding.txtAmount.setTextColor(ContextCompat.getColor(binding.root.context, R.color.colorPrimary))
            }
            binding.txtAmount.text = amount
            binding.txtName.text = transaction.note
            binding.txtDay.text = DateHelper.parseDate(
                "EEE MMM dd HH:mm:ss zzzz yyyy",
                "dd",
                transaction.createdAt.toDate().toString()
            )

            binding.root.setOnClickListener {
                onItemClick(transaction)
            }

            binding.root.isLongClickable = true
            binding.root.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            val edit = menu?.add(Menu.NONE, 0, 0, "Edit")
            val delete = menu?.add(Menu.NONE, 1, 0, "Delete")
            edit?.setOnMenuItemClickListener(onMenuClick)
            delete?.setOnMenuItemClickListener(onMenuClick)
        }

        val onMenuClick = MenuItem.OnMenuItemClickListener {
            when (it.itemId) {
                0 -> {
                    onMenuClick(0, getItem(adapterPosition))
                }
                1 -> {
                    onMenuClick(1, getItem(adapterPosition))
                }
            }
            true
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem == newItem
        }
    }
}