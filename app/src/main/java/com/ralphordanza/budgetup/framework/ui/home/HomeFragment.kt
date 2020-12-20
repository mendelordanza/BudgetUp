package com.ralphordanza.budgetup.framework.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ralphordanza.budgetup.databinding.FragmentHomeBinding
import com.ralphordanza.budgetup.framework.ui.home.HomeFragmentDirections
import com.ralphordanza.budgetup.framework.ui.wallets.WalletAdapter
import com.ralphordanza.budgetup.framework.ui.wallets.WalletViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var walletAdapter: WalletAdapter
    private val walletViewModel: WalletViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        walletViewModel.getWallets("OOH92EP8RrjjvDFX2kiN")
        observeData()
    }

    private fun setupRecyclerView() {
        walletAdapter = WalletAdapter{
            //On click wallet item
            val action = HomeFragmentDirections.actionHomeFragmentToTransactionsFragment(
                    it
                )
            findNavController().navigate(action)
        }
        binding.rvWallets.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = walletAdapter
        }
    }

    private fun observeData(){
        walletViewModel.getWallets().observe(viewLifecycleOwner, Observer { wallets ->
            walletAdapter.submitList(wallets)
        })
    }
}