package com.ralphordanza.budgetup.framework.ui.calculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.ralphordanza.budgetup.R
import com.ralphordanza.budgetup.databinding.FragmentCalculatorBinding
import com.ralphordanza.budgetup.framework.ui.transactions.AMOUNT_KEY
import com.ralphordanza.budgetup.framework.ui.transactions.REQUEST_AMOUNT

class CalculatorFragment : Fragment() {
    companion object {
        fun newInstance(param1: String, param2: String) = CalculatorFragment()
    }

    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachActions()
    }

    private fun attachActions(){
        binding.etAmount.setOnClickListener {
            val result = Bundle().apply {
                putString(AMOUNT_KEY, "5000")
            }
            setFragmentResult(REQUEST_AMOUNT, result)
            findNavController().navigateUp()
        }
    }
}