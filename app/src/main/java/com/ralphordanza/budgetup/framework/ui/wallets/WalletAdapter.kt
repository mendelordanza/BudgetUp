package com.ralphordanza.budgetup.framework.ui.wallets

import android.view.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ralphordanza.budgetup.databinding.ItemWalletBinding
import com.ralphordanza.budgetup.core.domain.model.Wallet
import com.ralphordanza.budgetup.framework.extensions.getDecimalString

class WalletAdapter(
    private val onClick: (wallet: Wallet) -> Unit,
    private val onMenuClick: (Int, Wallet) -> Unit
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
        RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener {
        fun bind(wallet: Wallet) {
            binding.txtName.text = wallet.name
            binding.txtAmount.text = wallet.amount.toDouble().getDecimalString()
            binding.root.setOnClickListener {
                onClick(wallet)
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
            val delete =menu?.add(Menu.NONE, 1, 0, "Delete")
            edit?.setOnMenuItemClickListener(onMenuClick)
            delete?.setOnMenuItemClickListener(onMenuClick)
        }

        val onMenuClick = MenuItem.OnMenuItemClickListener {
            when(it.itemId){
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

    class DiffCallback : DiffUtil.ItemCallback<Wallet>(){
        override fun areItemsTheSame(oldItem: Wallet, newItem: Wallet): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Wallet, newItem: Wallet): Boolean {
            return oldItem == newItem
        }

    }
}