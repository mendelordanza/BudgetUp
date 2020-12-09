package com.ralphordanza.budgetup.wallets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.FirebaseDatabase
import com.ralphordanza.budgetup.databinding.FragmentAddWalletBinding
import com.ralphordanza.budgetup.models.Wallet

class AddWalletFragment : Fragment() {
    companion object {
        fun newInstance() = AddWalletFragment()
    }

    private var _binding: FragmentAddWalletBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddWalletBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachActions()
    }

    private fun attachActions() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnAdd.setOnClickListener {
            saveToDb()
        }
    }

    private fun saveToDb() {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("wallets")

        val id = myRef.push().key ?: Long.MIN_VALUE.toString()
        val wallet = Wallet(
            id,
            binding.etName.text.toString(),
            binding.etAmount.text.toString()
        )
        myRef.child(id).setValue(wallet)
        findNavController().popBackStack()
    }
}