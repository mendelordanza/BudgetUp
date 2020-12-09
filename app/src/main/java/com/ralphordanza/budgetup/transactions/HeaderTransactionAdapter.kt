package com.ralphordanza.budgetup.transactions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ralphordanza.budgetup.databinding.ItemTransactionHeaderBinding
import com.ralphordanza.budgetup.models.Transaction
import com.ralphordanza.budgetup.models.TransactionSection

class HeaderTransactionAdapter(private val onClick: (transaction: Transaction) -> Unit) :
    ListAdapter<TransactionSection, HeaderTransactionAdapter.ViewHolder>(DiffCallback()) {

    private lateinit var binding: ItemTransactionHeaderBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HeaderTransactionAdapter.ViewHolder {
        binding = ItemTransactionHeaderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeaderTransactionAdapter.ViewHolder, position: Int) {
        val transactionSection = getItem(position) as TransactionSection
        holder.bind(transactionSection)
    }

    inner class ViewHolder(private val binding: ItemTransactionHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(transactionSection: TransactionSection) {

            if(transactionSection.items.isNotEmpty()){
                binding.txtMonth.text = transactionSection.month

                val itemTransactionAdapter = ItemTransactionAdapter { transaction ->
                    //On Item Click
                    onClick(transaction)
                }
                binding.rvTransactions.apply {
                    layoutManager = LinearLayoutManager(binding.root.context)
                    adapter = itemTransactionAdapter
                }
                itemTransactionAdapter.submitList(transactionSection.items)
            }
            else{
                binding.root.updateLayoutParams {
                    this.height = 0
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<TransactionSection>() {
        override fun areItemsTheSame(
            oldItem: TransactionSection,
            newItem: TransactionSection
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: TransactionSection,
            newItem: TransactionSection
        ): Boolean {
            return oldItem == newItem
        }
    }
}