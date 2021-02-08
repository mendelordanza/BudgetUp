package com.ralphordanza.budgetup.framework.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import com.ralphordanza.budgetup.R
import com.ralphordanza.budgetup.core.domain.model.Status
import com.ralphordanza.budgetup.core.domain.model.Wallet
import com.ralphordanza.budgetup.databinding.ActivityMainBinding
import com.ralphordanza.budgetup.framework.ui.calculator.CalculatorFragment
import com.ralphordanza.budgetup.framework.ui.login.LoginViewModel
import com.ralphordanza.budgetup.framework.ui.transactions.TransactionsFragmentDirections
import com.ralphordanza.budgetup.framework.ui.home.HomeFragmentDirections
import com.ralphordanza.budgetup.framework.ui.login.LoginActivity
import com.ralphordanza.budgetup.framework.ui.transactions.TransactionsFragmentArgs
import dagger.hilt.android.AndroidEntryPoint
import splitties.activities.start
import splitties.toast.toast
import splitties.views.imageResource

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

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

    private fun setupNavigation(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        setSupportActionBar(binding.appBar.toolbar)
        setupActionBarWithNavController(navController)

        var walletData: NavArgument? = null
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id){
                R.id.homeFragment -> {
                    supportActionBar?.hide()
                    binding.fab.setOnClickListener {
                        val action = HomeFragmentDirections.actionHomeFragmentToAddWalletFragment()
                        navController.navigate(action)
                    }
                    showBottomAppBar()

                    binding.fab.hide()
                    binding.fab.imageResource = R.drawable.ic_wallet
                    binding.fab.show()
                }
                R.id.transactionsFragment -> {
                    supportActionBar?.show()
                    binding.fab.setOnClickListener {
                        val action = TransactionsFragmentDirections.actionTransactionsFragmentToAddTransactionFragment()
                        navController.navigate(action)
                    }
                    showBottomAppBar()

                    binding.fab.hide()
                    binding.fab.imageResource = R.drawable.ic_transaction_white
                    binding.fab.show()
                }
                R.id.calculatorFragment -> {
                    supportActionBar?.show()
                    supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_check)
                    hideBottomAppBar()
                    binding.fab.hide()
                }
                R.id.addWalletFragment -> {
                    supportActionBar?.show()
                    hideBottomAppBar()
                    binding.fab.hide()
                }
                R.id.addTransactionFragment -> {
                    supportActionBar?.show()
                    hideBottomAppBar()
                    binding.fab.hide()
                }
            }
        }
    }

    private fun hideBottomAppBar(){
        binding.bottomAppBar.performHide()
    }

    private fun showBottomAppBar(){
        binding.bottomAppBar.performShow()
    }

    override fun onSupportNavigateUp(): Boolean {
        return if(navController.currentDestination?.id == R.id.calculatorFragment){
            setResultFromCalculator()
            true
        } else{
            navController.navigateUp() || super.onSupportNavigateUp()
        }
    }

    override fun onBackPressed() {
        return if(navController.currentDestination?.id == R.id.calculatorFragment){
            setResultFromCalculator()
        } else{
            super.onBackPressed()
        }
    }

    private fun setResultFromCalculator(){
        val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val frag = fragment?.childFragmentManager?.fragments?.get(0) as CalculatorFragment
        frag.checkResult()
    }

    private fun attachActions(){
        binding.bottomAppBar.setNavigationOnClickListener {
            toast("Navigation")
        }
        binding.bottomAppBar.setOnMenuItemClickListener {
            if(it.itemId == R.id.bottom_nav_more){
                loginViewModel.logout()
            }
            true
        }
    }

    private fun observerData(){
        loginViewModel.getLogout().observe(this, Observer { resource ->
            when(resource.status){
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    if(resource.data == null){
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
}