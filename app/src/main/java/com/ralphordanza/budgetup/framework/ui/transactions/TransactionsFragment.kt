package com.ralphordanza.budgetup.framework.ui.transactions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ralphordanza.budgetup.databinding.FragmentTransactionsBinding
import com.ralphordanza.budgetup.core.domain.Transaction
import com.ralphordanza.budgetup.core.domain.TransactionSection
import splitties.toast.toast

class TransactionsFragment : Fragment() {

    companion object {
        fun newInstance() = TransactionsFragment()
    }

    private lateinit var headerTransactionAdapter: HeaderTransactionAdapter
    private val args: TransactionsFragmentArgs by navArgs()

    private var _binding: FragmentTransactionsBinding? = null
    private val binding get() = _binding!!

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
        showDummyList()
        attachActions()
    }

    private fun setupUi() {
        binding.txtWallet.text = args.walletData.name
        binding.txtAmount.text = args.walletData.amount
    }

    private fun attachActions() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupTransactionList() {
        headerTransactionAdapter = HeaderTransactionAdapter { trans ->
            //TODO onClick
            toast(trans.wallet)
        }
        binding.rvGroupedTransactions.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = headerTransactionAdapter
        }
    }

    private fun showDummyList() {
        val dummyList1 = mutableListOf<Transaction>()
        dummyList1.add(
            Transaction(
                id = "0",
                date = "01",
                category = "Entertainment",
                type = "Income",
                amount = 1000.00,
                wallet = "BDO"
            )
        )
        dummyList1.add(
            Transaction(
                id = "1",
                date = "01",
                category = "Entertainment",
                type = "Income",
                amount = 1000.00,
                wallet = "BDO"
            )
        )
        dummyList1.add(
            Transaction(
                id = "2",
                date = "01",
                category = "Entertainment",
                type = "Income",
                amount = 1000.00,
                wallet = "BDO"
            )
        )
        dummyList1.add(
            Transaction(
                id = "3",
                date = "01",
                category = "Entertainment",
                type = "Income",
                amount = 1000.00,
                wallet = "BDO"
            )
        )
        dummyList1.add(
            Transaction(
                id = "4",
                date = "01",
                category = "Entertainment",
                type = "Income",
                amount = 1000.00,
                wallet = "BDO"
            )
        )
        dummyList1.add(
            Transaction(
                id = "5",
                date = "01",
                category = "Entertainment",
                type = "Income",
                amount = 1000.00,
                wallet = "BDO"
            )
        )

        val months = listOf("01", "02", "03", "04")
        val dummySection = months.map { month ->
            val filteredList = dummyList1.filter { trans ->
                trans.date == month
            }
            TransactionSection(Long.MIN_VALUE.toString(), month, filteredList)
        }


        headerTransactionAdapter.submitList(dummySection)
    }
}