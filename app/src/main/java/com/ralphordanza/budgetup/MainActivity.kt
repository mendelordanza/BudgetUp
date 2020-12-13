package com.ralphordanza.budgetup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.ralphordanza.budgetup.databinding.ActivityMainBinding
import com.ralphordanza.budgetup.transactions.TransactionsFragmentDirections
import com.ralphordanza.budgetup.wallets.AddWalletFragment
import com.ralphordanza.budgetup.wallets.HomeFragmentDirections
import kotlinx.android.synthetic.main.activity_main.*
import splitties.activities.start
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