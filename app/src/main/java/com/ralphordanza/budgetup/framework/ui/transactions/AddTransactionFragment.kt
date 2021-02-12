package com.ralphordanza.budgetup.framework.ui.transactions

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.datetime.datePicker
import com.ralphordanza.budgetup.R
import com.ralphordanza.budgetup.core.domain.model.Resource.Companion.DEFAULT_ERROR_MESSAGE
import com.ralphordanza.budgetup.core.domain.model.Status
import com.ralphordanza.budgetup.core.domain.model.Wallet
import com.ralphordanza.budgetup.databinding.FragmentAddTransactionBinding
import com.ralphordanza.budgetup.framework.ui.customview.LoadingDialog
import com.ralphordanza.budgetup.framework.ui.wallets.WalletViewModel
import com.ralphordanza.budgetup.framework.utils.Constants.AMOUNT
import com.ralphordanza.budgetup.framework.utils.Constants.DATE
import com.ralphordanza.budgetup.framework.utils.Constants.EXPENSE
import com.ralphordanza.budgetup.framework.utils.Constants.INCOME
import com.ralphordanza.budgetup.framework.utils.DateHelper
import com.ralphordanza.budgetup.framework.utils.ListHelper
import dagger.hilt.android.AndroidEntryPoint
import splitties.toast.toast

const val REQUEST_AMOUNT = "request_amount"
const val AMOUNT_KEY = "amount_key"

@AndroidEntryPoint
class AddTransactionFragment : Fragment() {

    private val viewModel: TransactionViewModel by viewModels()
    private val walletViewModel: WalletViewModel by viewModels()

    private var _binding: FragmentAddTransactionBinding? = null
    private val binding get() = _binding!!

    private var walletId = ""

    private lateinit var selectedWallet: Wallet
    private lateinit var selectedType: String
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog(requireActivity())

        viewModel.walletId()
        walletViewModel.userId()
        attachActions()
        listenAmount()
        loadWalletDropdown()
        observeData()
    }

    private fun listenAmount() {
        setFragmentResultListener(REQUEST_AMOUNT) { _, bundle ->
            val result = bundle.getString(AMOUNT_KEY)
            binding.etAmount.setText(result)
        }
    }

    private fun loadWalletDropdown() {
        walletViewModel.getUserId()
            .observe(viewLifecycleOwner, Observer { userId ->
                if (userId.isNotEmpty()) {
                    walletViewModel.getWallets(userId)
                }
            })
    }

    private fun attachActions() {
        val typeList = listOf(EXPENSE, INCOME)
        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            typeList
        )
        binding.etType.adapter = spinnerAdapter

        selectedType = typeList[0]
        binding.etType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedType = typeList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.etDate.setOnClickListener {
            MaterialDialog(requireContext()).show {
                datePicker { dialog, date ->
                    binding.etDate.setText(
                        DateHelper.parseDate(
                            "E MMM dd HH:mm:ss Z yyyy",
                            "MM/dd/yyyy",
                            date.time.toString()
                        )
                    )
                }
            }
        }

        binding.etAmount.setOnClickListener {
            val action =
                AddTransactionFragmentDirections.actionAddTransactionFragmentToCalculatorFragment(
                    binding.etAmount.text.toString()
                )
            findNavController().navigate(action)
        }

        binding.btnAdd.setOnClickListener {
            walletViewModel.getUserId()
                .observe(viewLifecycleOwner, Observer {
                    if(it.isNotEmpty()){
                        viewModel.addTransaction(
                            amount = binding.etAmount.text.toString(),
                            userId = it,
                            date = binding.etDate.text.toString(),
                            walletId = selectedWallet.id,
                            type = selectedType,
                            note = binding.etNote.text.toString()
                        )
                    }
                })
        }
    }

    private fun observeData() {
        viewModel.getWalletId().observe(viewLifecycleOwner, Observer {
            walletId = it
        })

        walletViewModel.getWallets().observe(viewLifecycleOwner, Observer { walletList ->
            //SETUP WALLET DROPDOWN
            val walletSpinnerAdapter = ArrayAdapter(
                requireContext(),
                R.layout.support_simple_spinner_dropdown_item,
                walletList.map { wallet -> wallet.name })
            binding.etWallet.adapter = walletSpinnerAdapter

            //SET INITIAL WALLET SELECTION
            val initialPos = walletList.indexOf(ListHelper.getWalletFromList(walletList, walletId))
            selectedWallet = walletList[initialPos]
            binding.etWallet.setSelection(initialPos)

            //LISTEN TO WALLET SELECTION
            binding.etWallet.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedWallet = walletList[position]
                    viewModel.storeWalletId(selectedWallet.id)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        })

        viewModel.getIsTransactionAdded().observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    loadingDialog.startLoadingDialog()
                }
                Status.SUCCESS -> {
                    loadingDialog.dismissLoadingDialog()
                    resource.data?.let {
                        (activity as OnTransactionChange).onTransactionChange(it)
                        findNavController().popBackStack()
                    }
                }
                Status.ERROR -> {
                    loadingDialog.dismissLoadingDialog()
                    when(resource.data){
                        AMOUNT -> binding.etAmount.error = resource.message
                        DATE -> binding.etDate.error = resource.message
                        else -> toast(resource.message ?: DEFAULT_ERROR_MESSAGE)
                    }
                }
            }
        })
    }

    interface OnTransactionChange {
        fun onTransactionChange(message: String)
    }
}