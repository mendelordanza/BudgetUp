package com.ralphordanza.budgetup.framework.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.DecelerateInterpolator
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavArgument
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import androidx.navigation.ui.setupActionBarWithNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.ralphordanza.budgetup.R
import com.ralphordanza.budgetup.core.domain.model.Status
import com.ralphordanza.budgetup.core.domain.model.Wallet
import com.ralphordanza.budgetup.databinding.ActivityMainBinding
import com.ralphordanza.budgetup.framework.extensions.setSafeOnClickListener
import com.ralphordanza.budgetup.framework.extensions.snackbar
import com.ralphordanza.budgetup.framework.ui.calculator.CalculatorFragment
import com.ralphordanza.budgetup.framework.ui.login.LoginViewModel
import com.ralphordanza.budgetup.framework.ui.transactions.TransactionsFragmentDirections
import com.ralphordanza.budgetup.framework.ui.home.HomeFragmentDirections
import com.ralphordanza.budgetup.framework.ui.login.LoginActivity
import com.ralphordanza.budgetup.framework.ui.transactions.AddTransactionFragment
import com.ralphordanza.budgetup.framework.ui.transactions.TransactionsFragment
import com.ralphordanza.budgetup.framework.ui.transactions.TransactionsFragmentArgs
import com.ralphordanza.budgetup.framework.ui.wallets.AddWalletFragment
import com.ralphordanza.budgetup.framework.utils.SnackbarListener
import dagger.hilt.android.AndroidEntryPoint
import splitties.activities.start
import splitties.toast.toast
import splitties.views.imageResource

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SnackbarListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupNavigation()
        attachActions()
        observerData()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        setSupportActionBar(binding.appBar.toolbar)
        setupActionBarWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.homeFragment -> {
                    binding.fab.setSafeOnClickListener {
                        val action = HomeFragmentDirections.actionHomeFragmentToAddWalletFragment(null)
                        navController.navigate(action)
                    }
                    hideToolbar()
                    showBottomAppBar()

                    binding.fab.hide()
                    binding.fab.imageResource = R.drawable.ic_wallet
                    binding.fab.show()
                }
                R.id.transactionsFragment -> {
                    binding.fab.setSafeOnClickListener {
                        val action = TransactionsFragmentDirections.actionTransactionsFragmentToAddTransactionFragment()
                        navController.navigate(action)
                    }
                    showToolbar()
                    showBottomAppBar()

                    binding.fab.hide()
                    binding.fab.imageResource = R.drawable.ic_transaction_white
                    binding.fab.show()
                }
                R.id.calculatorFragment -> {
                    supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_check)
                    showToolbar()
                    hideBottomAppBar()
                    binding.fab.hide()
                }
                R.id.addWalletFragment -> {
                    showToolbar()
                    hideBottomAppBar()
                    binding.fab.hide()
                }
                R.id.addTransactionFragment -> {
                    showToolbar()
                    hideBottomAppBar()
                    binding.fab.hide()
                }
            }
        }
    }

    private fun showToolbar(){
        supportActionBar?.show()
    }

    private fun hideToolbar(){
        supportActionBar?.hide()
    }

    private fun hideBottomAppBar() {
        binding.bottomAppBar.performHide()
    }

    private fun showBottomAppBar() {
        binding.bottomAppBar.performShow()
    }

    override fun onSupportNavigateUp(): Boolean {
        return if (navController.currentDestination?.id == R.id.calculatorFragment) {
            setResultFromCalculator()
            true
        } else {
            navController.navigateUp() || super.onSupportNavigateUp()
        }
    }

    override fun onBackPressed() {
        return if (navController.currentDestination?.id == R.id.calculatorFragment) {
            setResultFromCalculator()
        } else {
            super.onBackPressed()
        }
    }

    private fun setResultFromCalculator() {
        val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val frag = fragment?.childFragmentManager?.fragments?.get(0) as CalculatorFragment
        frag.checkResult()
    }

    private fun attachActions() {
        binding.bottomAppBar.setNavigationOnClickListener {
            MaterialDialog(this, BottomSheet())
                .customView(R.layout.bottom_sheet)
                .cornerRadius(16f)
                .show()
        }
        binding.bottomAppBar.setOnMenuItemClickListener {
            if (it.itemId == R.id.bottom_nav_more) {
                loginViewModel.logout()
            }
            true
        }
    }

    private fun observerData() {
        loginViewModel.getLogout().observe(this, Observer { resource ->
            when (resource.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    if (resource.data == null) {
                        loginViewModel.clearSession()
                        start<LoginActivity>()
                        finishAffinity()
                    }
                }
                Status.ERROR -> {
                    toast(resource.message.toString())
                }
            }
        })
    }

    override fun onTransactionChange(message: String) {
        message.snackbar(binding.root, binding.fab)
    }

    override fun onWalletChange(message: String) {
        message.snackbar(binding.root, binding.fab)
    }
}