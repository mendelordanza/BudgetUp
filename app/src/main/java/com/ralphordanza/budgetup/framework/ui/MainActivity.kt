package com.ralphordanza.budgetup.framework.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.ralphordanza.budgetup.R
import com.ralphordanza.budgetup.databinding.ActivityMainBinding
import com.ralphordanza.budgetup.framework.ui.login.LoginViewModel
import com.ralphordanza.budgetup.framework.ui.transactions.TransactionsFragmentDirections
import com.ralphordanza.budgetup.framework.ui.home.HomeFragmentDirections
import com.ralphordanza.budgetup.framework.ui.login.LoginActivity
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
                R.id.addWalletFragment, R.id.addTransactionFragment -> {
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
        return navController.navigateUp() || super.onSupportNavigateUp()
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
        loginViewModel.getLogout().observe(this, Observer { isLoggedOut ->
            if(isLoggedOut){
                start<LoginActivity>()
                finishAffinity()
            }
        })
    }
}