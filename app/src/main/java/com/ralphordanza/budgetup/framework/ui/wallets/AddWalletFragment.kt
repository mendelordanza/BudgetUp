package com.ralphordanza.budgetup.framework.ui.wallets

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ralphordanza.budgetup.R
import com.ralphordanza.budgetup.core.domain.model.Resource
import com.ralphordanza.budgetup.core.domain.model.Status
import com.ralphordanza.budgetup.core.domain.model.Wallet
import com.ralphordanza.budgetup.databinding.FragmentAddWalletBinding
import com.ralphordanza.budgetup.framework.extensions.hideKeyboard
import com.ralphordanza.budgetup.framework.ui.customview.LoadingDialog
import com.ralphordanza.budgetup.framework.ui.transactions.AMOUNT_KEY
import com.ralphordanza.budgetup.framework.ui.transactions.AddTransactionFragmentDirections
import com.ralphordanza.budgetup.framework.ui.transactions.REQUEST_AMOUNT
import com.ralphordanza.budgetup.framework.utils.Constants
import com.ralphordanza.budgetup.framework.utils.SnackbarListener
import dagger.hilt.android.AndroidEntryPoint
import splitties.toast.toast

@AndroidEntryPoint
class AddWalletFragment : Fragment() {
    companion object {
        fun newInstance() = AddWalletFragment()
    }

    private var _binding: FragmentAddWalletBinding? = null
    private val binding get() = _binding!!

    private val args: AddWalletFragmentArgs by navArgs()
    private val walletViewModel: WalletViewModel by viewModels()
    private lateinit var loadingDialog: LoadingDialog

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

        loadingDialog = LoadingDialog(requireActivity())

        args.walletData?.let { wallet ->//IF NOT NULL
            binding.etName.setText(wallet.name)
            binding.etAmount.setText(wallet.amount)
            binding.btnAdd.text = "Update"
        } ?: run { //ELSE IF NULL

        }

        walletViewModel.userId()
        setupAssets()
        attachActions()
        listenAmount()
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

    private fun listenAmount() {
        setFragmentResultListener(REQUEST_AMOUNT) { _, bundle ->
            val result = bundle.getString(AMOUNT_KEY)
            binding.etAmount.setText(result)
        }
    }

    private fun attachActions() {
        binding.etAmount.setOnClickListener {
            hideKeyboard()
            val action = AddWalletFragmentDirections.actionAddWalletFragmentToCalculatorFragment(
                binding.etAmount.text.toString()
            )
            findNavController().navigate(action)
        }

        binding.btnAdd.setOnClickListener {
            walletViewModel.getUserId()
                .observe(viewLifecycleOwner, Observer {
                    if (it.isNotEmpty()) {
                        args.walletData?.let { wallet ->
                            walletViewModel.getUserId()
                                .observe(viewLifecycleOwner, Observer { userId ->
                                    if (userId.isNotEmpty()) {
                                        walletViewModel.updateWallet(
                                            binding.etAmount.text.toString(),
                                            binding.etName.text.toString(),
                                            wallet.id,
                                            userId
                                        )
                                    }
                                })
                        } ?: run {
                            walletViewModel.addWallet(
                                it,
                                binding.etName.text.toString(),
                                binding.etAmount.text.toString()
                            )
                        }
                    }
                })
        }
    }

    private fun observeData() {
        walletViewModel.getIsUpdated().observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    loadingDialog.startLoadingDialog()
                }
                Status.SUCCESS -> {
                    loadingDialog.dismissLoadingDialog()
                    resource.data?.let {
                        (activity as SnackbarListener).onWalletChange(it)
                        findNavController().popBackStack()
                    }
                }
                Status.ERROR -> {
                    loadingDialog.dismissLoadingDialog()
                    when (resource.data) {
                        Constants.WALLET_NAME -> binding.etName.error = resource.message
                        Constants.AMOUNT -> binding.etAmount.error = resource.message
                        else -> toast(resource.message ?: Resource.DEFAULT_ERROR_MESSAGE)
                    }
                }
            }
        })

        walletViewModel.getIsAdded().observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    loadingDialog.startLoadingDialog()
                }
                Status.SUCCESS -> {
                    loadingDialog.dismissLoadingDialog()
                    resource.data?.let {
                        (activity as SnackbarListener).onWalletChange(it)
                        findNavController().popBackStack()
                    }
                }
                Status.ERROR -> {
                    loadingDialog.dismissLoadingDialog()
                    when (resource.data) {
                        Constants.WALLET_NAME -> binding.etName.error = resource.message
                        Constants.AMOUNT -> binding.etAmount.error = resource.message
                        else -> toast(resource.message ?: Resource.DEFAULT_ERROR_MESSAGE)
                    }
                }
            }
        })
    }
}