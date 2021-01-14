package com.ralphordanza.budgetup.framework.ui.transactions

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.ralphordanza.budgetup.R
import com.ralphordanza.budgetup.databinding.FragmentAddTransactionBinding
import dagger.hilt.android.AndroidEntryPoint
import splitties.toast.toast

const val REQUEST_AMOUNT = "request_amount"
const val AMOUNT_KEY = "amount_key"

@AndroidEntryPoint
class AddTransactionFragment : Fragment() {

    private val viewModel: TransactionViewModel by viewModels()

    private var _binding: FragmentAddTransactionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachActions()
        listenAmount()
        observeData()
    }

    private fun listenAmount() {
        setFragmentResultListener(REQUEST_AMOUNT) { _, bundle ->
            val result = bundle.getString(AMOUNT_KEY)
            binding.etAmount.setText(result)
        }
    }

    private fun attachActions() {
        binding.etAmount.setOnClickListener {
            val action =
                AddTransactionFragmentDirections.actionAddTransactionFragmentToCalculatorFragment(
                    binding.etAmount.text.toString()
                )
            findNavController().navigate(action)
        }

        binding.btnAdd.setOnClickListener {
            viewModel.getSessionManager().userIdFlow.asLiveData().observe(viewLifecycleOwner, Observer {
                //viewModel.addTransaction(userId = it, walletId = "V82OqRa9vSx7EIHrRrdR")
            })
        }
    }

    private fun observeData(){
        viewModel.getIsTransactionAdded().observe(viewLifecycleOwner, Observer { isAdded ->
            if(isAdded){
                findNavController().popBackStack()
            }
        })
    }
}