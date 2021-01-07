package com.ralphordanza.budgetup.framework.ui.wallets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ralphordanza.budgetup.databinding.ItemWalletBinding
import com.ralphordanza.budgetup.core.domain.model.Wallet

class WalletAdapter(
    private val onClick: (wallet: Wallet) -> Unit
) : ListAdapter<Wallet, WalletAdapter.ViewHolder>(DiffCallback()) {
    private lateinit var binding: ItemWalletBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemWalletBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wallet = getItem(position)
        holder.bind(wallet)
    }

    inner class ViewHolder(private val binding: ItemWalletBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(wallet: Wallet) {
            binding.txtName.text = wallet.name
            binding.txtAmount.text = wallet.amount
            binding.root.setOnClickListener {
                onClick(wallet)
            }
            binding.root.isLongClickable = true
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Wallet>(){
        override fun areItemsTheSame(oldItem: Wallet, newItem: Wallet): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Wallet, newItem: Wallet): Boolean {
            return oldItem == newItem
        }

    }

}