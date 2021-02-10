package com.ralphordanza.budgetup.framework.ui.transactions

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ralphordanza.budgetup.databinding.FragmentTransactionsBinding
import com.ralphordanza.budgetup.core.domain.model.Transaction
import com.ralphordanza.budgetup.core.domain.model.TransactionSection
import com.ralphordanza.budgetup.framework.extensions.getDecimalString
import com.ralphordanza.budgetup.framework.utils.DateHelper
import dagger.hilt.android.AndroidEntryPoint
import splitties.toast.toast

@AndroidEntryPoint
class TransactionsFragment : Fragment() {

    companion object {
        fun newInstance() = TransactionsFragment()
    }

    private lateinit var headerTransactionAdapter: HeaderTransactionAdapter
    private val args: TransactionsFragmentArgs by navArgs()

    private var _binding: FragmentTransactionsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TransactionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransactionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
        setupTransactionList()
        viewModel.storeWalletId(args.walletData.id)
        viewModel.userId()
        observeData()
    }

    private fun setupUi() {
        binding.txtWallet.text = args.walletData.name
        binding.txtAmount.text = args.walletData.amount.toDouble().getDecimalString()
    }

    private fun setupTransactionList() {
        headerTransactionAdapter = HeaderTransactionAdapter { trans ->
            //TODO onClick edit transaction
            toast(trans.note)
        }
        binding.rvGroupedTransactions.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = headerTransactionAdapter
        }
    }

    private fun observeData() {
        viewModel.getUserId().observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty()){
                viewModel.loadTransactions(it, args.walletData.id)
            }
        })

        viewModel.getTransactions().observe(viewLifecycleOwner, Observer {
            headerTransactionAdapter.submitList(it)
        })
    }
}