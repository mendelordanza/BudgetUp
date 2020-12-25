package com.ralphordanza.budgetup.framework.ui.transactions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.ralphordanza.budgetup.R
import com.ralphordanza.budgetup.databinding.FragmentAddTransactionBinding
import splitties.toast.toast

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
    }

    private fun attachActions(){
        binding.etAmount.setOnClickListener {
            toast("Redirect to calculator")
        }
    }
}