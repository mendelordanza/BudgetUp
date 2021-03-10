package com.ralphordanza.budgetup.framework.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ralphordanza.budgetup.core.domain.model.Status
import com.ralphordanza.budgetup.databinding.FragmentHomeBinding
import com.ralphordanza.budgetup.framework.extensions.getDecimalString
import com.ralphordanza.budgetup.framework.ui.wallets.WalletAdapter
import com.ralphordanza.budgetup.framework.ui.wallets.WalletViewModel
import com.ralphordanza.budgetup.framework.utils.SnackbarListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
        walletViewModel.userId()
        loadWallets()
        observeData()
    }

    private fun loadWallets() {
        walletViewModel.getUserId().observe(viewLifecycleOwner, Observer { userId ->
            if (userId.isNotEmpty()) {
                walletViewModel.getTotal(userId)
                walletViewModel.getWallets(userId)
            }
        })
    }

    private fun setupRecyclerView() {
        walletAdapter = WalletAdapter({
            //On click wallet item
            val action = HomeFragmentDirections.actionHomeFragmentToTransactionsFragment(
                it
            )
            findNavController().navigate(action)
        }, { type, wallet ->
            when (type) {
                0 -> { //Edit

                }
                1 -> { //Delete
                    walletViewModel.getUserId().observe(viewLifecycleOwner, Observer { userId ->
                        if (userId.isNotEmpty()) {
                            walletViewModel.deleteWallet(userId, wallet)
                        }
                    })
                }
            }
        })
        binding.rvWallets.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = walletAdapter
        }
    }

    private fun observeData() {
        walletViewModel.getTotal().observe(viewLifecycleOwner, Observer {
            binding.txtAmount.text = it.getDecimalString()
        })

        walletViewModel.getWallets().observe(viewLifecycleOwner, Observer { wallets ->
            walletAdapter.submitList(wallets)
        })

        lifecycleScope.launch {
            walletViewModel.eventFlow.collect { event ->
                when (event) {
                    is WalletViewModel.WalletEvent.WalletDeleteEvent -> {
                        when (event.resource.status) {
                            Status.LOADING -> {

                            }
                            Status.SUCCESS -> {
                                event.resource.data?.let {
                                    (activity as SnackbarListener).onWalletChange(it)
                                    loadWallets()
                                }
                            }
                            Status.ERROR -> {

                            }
                        }
                    }
                }
            }
        }
    }
}