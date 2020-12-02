package com.ralphordanza.budgetup.wallets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ralphordanza.budgetup.R

class AddWalletFragment : Fragment() {
    companion object {
        fun newInstance() = AddWalletFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_wallet, container, false)
    }
}