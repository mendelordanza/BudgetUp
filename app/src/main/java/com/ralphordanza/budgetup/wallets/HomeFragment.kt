package com.ralphordanza.budgetup.wallets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ralphordanza.budgetup.databinding.FragmentHomeBinding
import com.ralphordanza.budgetup.models.Wallet

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var walletAdapter: WalletAdapter

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
        showDummyData()
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
    }

    private fun showDummyData(){
//        val dummyList = mutableListOf<Wallet>()
//        dummyList.add(Wallet("0", "BDO", "PHP 5,000.00"))
//        dummyList.add(Wallet("1", "ING", "PHP 10,000.00"))
//        dummyList.add(Wallet("2", "RBank", "PHP 5,000.00"))
//        dummyList.add(Wallet("3", "Cash", "PHP 9,000.00"))
//        dummyList.add(Wallet("0", "BDO", "PHP 5,000.00"))
//        dummyList.add(Wallet("1", "ING", "PHP 10,000.00"))
//        dummyList.add(Wallet("2", "RBank", "PHP 5,000.00"))
//        dummyList.add(Wallet("3", "Cash", "PHP 9,000.00"))
//        walletAdapter.updateList(dummyList)

        val database = FirebaseDatabase.getInstance()
        val walletRef = database.getReference("wallets")

        walletRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
//                val list = snapshot.children.map {
//                    val wallet = snapshot.getValue(Wallet::class.java)
//                    wallet
//                }
                val list = mutableListOf<Wallet>()
                for (wallet in snapshot.children) {
                    val item = wallet.getValue(Wallet::class.java)
                    if (item != null) {
                        list.add(item)
                    }
                }
                walletAdapter.submitList(list)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}