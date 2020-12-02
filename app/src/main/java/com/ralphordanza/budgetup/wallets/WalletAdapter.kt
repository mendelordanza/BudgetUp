package com.ralphordanza.budgetup.wallets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ralphordanza.budgetup.databinding.ItemWalletBinding
import com.ralphordanza.budgetup.models.Wallet

class WalletAdapter(
    private val walletList: MutableList<Wallet>,
    private val onClick: (wallet: Wallet) -> Unit
) : RecyclerView.Adapter<WalletAdapter.ViewHolder>() {
    private lateinit var binding: ItemWalletBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletAdapter.ViewHolder {
        binding = ItemWalletBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = walletList.size

    override fun onBindViewHolder(holder: WalletAdapter.ViewHolder, position: Int) {
        val wallet = walletList[position]
        holder.bind(wallet)
    }

    fun updateList(newList: MutableList<Wallet>) {
        walletList.clear()
        walletList.addAll(newList)
        notifyDataSetChanged()
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

}