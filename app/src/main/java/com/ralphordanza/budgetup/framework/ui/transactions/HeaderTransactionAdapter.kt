package com.ralphordanza.budgetup.framework.ui.transactions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ralphordanza.budgetup.databinding.ItemTransactionHeaderBinding
import com.ralphordanza.budgetup.core.domain.model.Transaction
import com.ralphordanza.budgetup.core.domain.model.TransactionSection

class HeaderTransactionAdapter(
    private val onClick: (transaction: Transaction) -> Unit,
    private val onMenuClick: (Int, Transaction) -> Unit
) :
    ListAdapter<TransactionSection, HeaderTransactionAdapter.ViewHolder>(DiffCallback()) {

    private lateinit var binding: ItemTransactionHeaderBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemTransactionHeaderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transactionSection = getItem(position) as TransactionSection
        holder.bind(transactionSection)
    }

    inner class ViewHolder(private val binding: ItemTransactionHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(transactionSection: TransactionSection) {
            if (transactionSection.items.isNotEmpty()) {
                val params = binding.root.layoutParams
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                params.width = ViewGroup.LayoutParams.MATCH_PARENT
                binding.root.layoutParams = params

                binding.txtMonth.text = transactionSection.month

                val itemTransactionAdapter = ItemTransactionAdapter(
                    { transaction ->
                        //On Item Click
                        onClick(transaction)
                    },
                    { type, transaction ->
                        onMenuClick(type, transaction)
                    }
                )
                binding.rvTransactions.apply {
                    layoutManager = LinearLayoutManager(binding.root.context)
                    adapter = itemTransactionAdapter
                }
                itemTransactionAdapter.submitList(transactionSection.items)
            } else {
                val params = binding.root.layoutParams
                params.height = 0
                params.width = 0
                binding.root.layoutParams = params
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