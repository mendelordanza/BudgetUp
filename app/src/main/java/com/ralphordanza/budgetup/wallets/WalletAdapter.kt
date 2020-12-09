package com.ralphordanza.budgetup.wallets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ralphordanza.budgetup.databinding.ItemWalletBinding
import com.ralphordanza.budgetup.models.Wallet

class WalletAdapter(
    private val onClick: (wallet: Wallet) -> Unit
) : ListAdapter<Wallet, WalletAdapter.ViewHolder>(DiffCallback()) {
    private lateinit var binding: ItemWalletBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletAdapter.ViewHolder {
        binding = ItemWalletBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WalletAdapter.ViewHolder, position: Int) {
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