package com.ralphordanza.budgetup.transactions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ralphordanza.budgetup.R

class AddTransactionFragment : Fragment() {
    companion object {
        fun newInstance(param1: String, param2: String) = AddTransactionFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_transaction, container, false)
    }
}