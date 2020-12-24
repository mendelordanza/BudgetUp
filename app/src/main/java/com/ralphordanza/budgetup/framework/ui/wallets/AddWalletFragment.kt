package com.ralphordanza.budgetup.framework.ui.wallets

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.ralphordanza.budgetup.R
import com.ralphordanza.budgetup.databinding.FragmentAddWalletBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddWalletFragment : Fragment() {
    companion object {
        fun newInstance() = AddWalletFragment()
    }

    private var _binding: FragmentAddWalletBinding? = null
    private val binding get() = _binding!!

    private val walletViewModel: WalletViewModel by viewModels()

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

        setupAssets()
        attachActions()
        observeData()
    }

    private fun setupAssets() {
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.iconWallet.setImageResource(R.drawable.ic_add_wallet_light)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.iconWallet.setImageResource(R.drawable.ic_add_wallet)
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        when (newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.iconWallet.setImageResource(R.drawable.ic_add_wallet_light)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.iconWallet.setImageResource(R.drawable.ic_add_wallet)
            }
        }
    }

    private fun attachActions() {
        binding.btnAdd.setOnClickListener {
            walletViewModel.getSessionManager().userIdFlow.asLiveData()
                .observe(viewLifecycleOwner, Observer {
                    walletViewModel.addWallet(
                        it,
                        binding.etName.text.toString(),
                        binding.etAmount.text.toString()
                    )
                })
        }
    }

    private fun observeData() {
        walletViewModel.getIsAdded().observe(viewLifecycleOwner, Observer { walletAdded ->
            if (walletAdded) {
                findNavController().popBackStack()
            }
        })
    }
}