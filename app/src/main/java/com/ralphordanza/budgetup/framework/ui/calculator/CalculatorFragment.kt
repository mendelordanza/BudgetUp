package com.ralphordanza.budgetup.framework.ui.calculator

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ralphordanza.budgetup.R
import com.ralphordanza.budgetup.databinding.FragmentCalculatorBinding
import com.ralphordanza.budgetup.framework.ui.transactions.AMOUNT_KEY
import com.ralphordanza.budgetup.framework.ui.transactions.REQUEST_AMOUNT
import com.ralphordanza.budgetup.framework.ui.transactions.TransactionsFragmentArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_calculator.*

@AndroidEntryPoint
class CalculatorFragment : Fragment() {
    companion object {
        fun newInstance(param1: String, param2: String) = CalculatorFragment()
    }

    private val calculatorViewModel: CalculatorViewModel by viewModels()
    private val args: CalculatorFragmentArgs by navArgs()

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

        setupExistingAmount()
        setupCalculator()
        observeData()
    }

    private fun setupExistingAmount(){
        binding.etDisplay.setText(args.amount)
        binding.etDisplay.setSelection(args.amount.length)
    }

    private fun setupCalculator() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            binding.etDisplay.showSoftInputOnFocus = false
        }

        val buttonList = mutableListOf(
            binding.btnBackspace,

            //Row 1
            binding.btnClear,
            binding.btnPar,
            binding.btnExp,
            binding.btnDivide,

            //Row 2
            binding.btn7,
            binding.btn8,
            binding.btn9,
            binding.btnMultiply,

            //Row 3
            binding.btn4,
            binding.btn5,
            binding.btn6,
            binding.btnSubtract,

            //Row 4
            binding.btn1,
            binding.btn2,
            binding.btn3,
            binding.btnPlus,

            //Row
            binding.btnPosNeg,
            binding.btn0,
            binding.btnPeriod,
            binding.btnEqual
        )

        for (button in buttonList) {
            button.setOnClickListener {
                when (it) {
                    binding.btnClear -> binding.etDisplay.setText("")
                    binding.btn0 -> updateText("0")
                    binding.btn1 -> updateText("1")
                    binding.btn2 -> updateText("2")
                    binding.btn3 -> updateText("3")
                    binding.btn4 -> updateText("4")
                    binding.btn5 -> updateText("5")
                    binding.btn6 -> updateText("6")
                    binding.btn7 -> updateText("7")
                    binding.btn8 -> updateText("8")
                    binding.btn9 -> updateText("9")
                    binding.btnPeriod -> updateText(".")
                    binding.btnPlus -> updateText("+")
                    binding.btnSubtract -> updateText("-")
                    binding.btnMultiply -> updateText("×")
                    binding.btnDivide -> updateText("÷")
                    binding.btnPosNeg -> updateText("-")
                    binding.btnExp -> updateText("^")
                    binding.btnPar -> parenthesis()
                    binding.btnBackspace -> backspace()
                    binding.btnEqual -> calculatorViewModel.compute(binding.etDisplay.text.toString())
                }
            }
        }
    }

    private fun parenthesis(){
        val cursorPos = binding.etDisplay.selectionStart
        var openPar = 0
        var closedPar = 0
        val txtLength = binding.etDisplay.text.length

        for(i in 0 until cursorPos){
            if(binding.etDisplay.text.toString().substring(i, i + 1) == "("){
                openPar += 1
            }
            if(binding.etDisplay.text.toString().substring(i, i + 1) == ")"){
                closedPar += 1
            }
        }

        if(openPar == closedPar || binding.etDisplay.text.toString().substring(txtLength - 1, txtLength) == "("){
            updateText("(")
        }
        else if(closedPar < openPar && binding.etDisplay.text.toString().substring(txtLength - 1, txtLength) != "("){
            updateText(")")
        }
        binding.etDisplay.setSelection(cursorPos + 1)
    }

    private fun backspace(){
        val cursorPos = binding.etDisplay.selectionStart
        val txtLength = binding.etDisplay.text.length

        if(cursorPos != 0 && txtLength != 0){
            val selection = binding.etDisplay.text as SpannableStringBuilder
            selection.replace(cursorPos - 1, cursorPos, "")
            binding.etDisplay.text = selection
            binding.etDisplay.setSelection(cursorPos - 1)
        }
    }

    private fun updateText(num: String){
        binding.apply {
            val oldStr = etDisplay.text.toString()
            val cursorPos = etDisplay.selectionStart
            val leftStr = oldStr.substring(0, cursorPos)
            val rightStr = oldStr.substring(cursorPos)

            etDisplay.setText(String.format("%s%s%s", leftStr, num, rightStr))
            etDisplay.setSelection(cursorPos + 1)
        }
    }

    private fun observeData() {
        calculatorViewModel.getResult().observe(viewLifecycleOwner, Observer {
            binding.etDisplay.setText(it)
            binding.etDisplay.setSelection(it.length)
        })
    }

    //User clicked done
    fun setResult() {
        if (binding.etDisplay.text.toString().contains("+") ||
            binding.etDisplay.text.toString().contains("-") ||
            binding.etDisplay.text.toString().contains("×") ||
            binding.etDisplay.text.toString().contains("÷")
        ) {
            calculatorViewModel.compute(binding.etDisplay.text.toString())
        }
        else{
            val result = Bundle().apply {
                putString(AMOUNT_KEY, binding.etDisplay.text.toString())
            }
            setFragmentResult(REQUEST_AMOUNT, result)
            findNavController().navigateUp()

        }
    }
}