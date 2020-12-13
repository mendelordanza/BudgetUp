package com.ralphordanza.budgetup.framework.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.ralphordanza.budgetup.R
import com.ralphordanza.budgetup.databinding.ActivityMainBinding
import com.ralphordanza.budgetup.framework.ui.transactions.TransactionsFragmentDirections
import com.ralphordanza.budgetup.framework.ui.wallets.HomeFragmentDirections
import splitties.toast.toast
import splitties.views.imageResource

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupNavigation()
        attachActions()
    }

    private fun setupNavigation(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id){
                R.id.homeFragment -> {
                    binding.fab.setOnClickListener {
                        val action = HomeFragmentDirections.actionHomeFragmentToAddWalletFragment()
                        navController.navigate(action)
                    }
                    binding.bottomAppBar.performShow()

                    binding.fab.hide()
                    binding.fab.imageResource = R.drawable.ic_wallet
                    binding.fab.show()
                }
                R.id.transactionsFragment -> {
                    binding.fab.setOnClickListener {
                        val action = TransactionsFragmentDirections.actionTransactionsFragmentToAddTransactionFragment()
                        navController.navigate(action)
                    }
                    binding.bottomAppBar.performShow()

                    binding.fab.hide()
                    binding.fab.imageResource = R.drawable.ic_transaction_white
                    binding.fab.show()
                }
                R.id.addWalletFragment, R.id.addTransactionFragment -> {
                    binding.bottomAppBar.performHide()
                    binding.fab.hide()
                }
            }
        }
    }

    private fun attachActions(){
        binding.bottomAppBar.setNavigationOnClickListener {
            toast("Navigation")
        }
    }
}