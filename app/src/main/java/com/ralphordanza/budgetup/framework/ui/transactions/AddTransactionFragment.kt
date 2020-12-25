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
import androidx.navigation.fragment.findNavController
import com.ralphordanza.budgetup.R
import com.ralphordanza.budgetup.databinding.FragmentAddTransactionBinding
import splitties.toast.toast

const val REQUEST_AMOUNT = "request_amount"
const val AMOUNT_KEY = "amount_key"

class AddTransactionFragment : Fragment() {
    companion object {
        fun newInstance(param1: String, param2: String) = AddTransactionFragment()
    }

    private var _binding: FragmentAddTransactionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachActions()
        listenAmount()
    }

    private fun listenAmount(){
        setFragmentResultListener(REQUEST_AMOUNT) { _, bundle ->
            val result = bundle.getString(AMOUNT_KEY)
            binding.etAmount.setText(result)
        }
    }

    private fun attachActions(){
        binding.etAmount.setOnClickListener {
            val action = AddTransactionFragmentDirections.actionAddTransactionFragmentToCalculatorFragment()
            findNavController().navigate(action)
        }
    }
}