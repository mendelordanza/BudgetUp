package com.ralphordanza.budgetup.framework.ui.home

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ralphordanza.budgetup.R
import com.ralphordanza.budgetup.core.domain.model.Wallet
import com.ralphordanza.budgetup.databinding.FragmentHomeBinding
import com.ralphordanza.budgetup.framework.ui.home.HomeFragmentDirections
import com.ralphordanza.budgetup.framework.ui.wallets.WalletAdapter
import com.ralphordanza.budgetup.framework.ui.wallets.WalletViewModel
import dagger.hilt.android.AndroidEntryPoint
import splitties.toast.toast

@AndroidEntryPoint
class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var walletAdapter: WalletAdapter
    private val walletViewModel: WalletViewModel by viewModels()
    private var itemPos = 0

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
        loadWallets()
        observeData()
    }

    private fun loadWallets() {
        walletViewModel.getSessionManager().userIdFlow.asLiveData()
            .observe(viewLifecycleOwner, Observer { userId ->
                walletViewModel.getTotal(userId)
                walletViewModel.getWallets(userId)
            })
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
        registerForContextMenu(binding.rvWallets)
    }

    private fun observeData() {
        walletViewModel.getTotal().observe(viewLifecycleOwner, Observer {
            binding.txtAmount.text = it.toString()
        })

        walletViewModel.getWallets().observe(viewLifecycleOwner, Observer { wallets ->
            walletAdapter.submitList(wallets)
        })
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        val inflater = activity?.menuInflater as MenuInflater
        inflater.inflate(R.menu.recycler_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.recycler_edit -> {
                //TODO Edit Wallet
                toast("Edit")
            }
            R.id.recycler_delete -> {
                walletViewModel.getSessionManager().userIdFlow.asLiveData()
                    .observe(viewLifecycleOwner, Observer { id ->
                        walletViewModel.deleteWallet(id, walletAdapter.currentList[item.order])
                        loadWallets()
                    })
            }
        }
        return super.onContextItemSelected(item)
    }
}