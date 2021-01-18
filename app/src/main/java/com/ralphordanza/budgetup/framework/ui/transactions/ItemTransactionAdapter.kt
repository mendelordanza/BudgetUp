package com.ralphordanza.budgetup.framework.ui.transactions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ralphordanza.budgetup.databinding.ItemTransactionBinding
import com.ralphordanza.budgetup.core.domain.model.Transaction
import com.ralphordanza.budgetup.framework.utils.DateHelper

class ItemTransactionAdapter(private val onItemClick: (transaction: Transaction) -> Unit) :
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
        RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction) {
            binding.txtAmount.text = transaction.amount
            binding.txtNotes.text = transaction.note
            binding.txtDay.text = DateHelper.parseDate(
                "EEE MMM dd HH:mm:ss zzzz yyyy",
                "dd",
                transaction.createdAt.toDate().toString())

            binding.root.setOnClickListener {
                onItemClick(transaction)
            }
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