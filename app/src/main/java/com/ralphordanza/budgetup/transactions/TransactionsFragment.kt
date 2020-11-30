package com.ralphordanza.budgetup.transactions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ralphordanza.budgetup.databinding.FragmentTransactionsBinding

class TransactionsFragment : Fragment() {

    companion object {
        fun newInstance() = TransactionsFragment()
    }

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
        attachActions()
    }

    private fun setupUi(){
        binding.txtWallet.text = args.walletData.name
        binding.txtAmount.text = args.walletData.amount
    }

    private fun attachActions(){
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}