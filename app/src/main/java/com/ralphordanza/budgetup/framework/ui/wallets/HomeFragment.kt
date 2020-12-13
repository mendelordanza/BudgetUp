package com.ralphordanza.budgetup.framework.ui.wallets

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ralphordanza.budgetup.databinding.FragmentHomeBinding

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

        //TODO create clean architecture
        FirebaseAuth.getInstance().currentUser?.uid?.let { id ->
            val docRef =
                FirebaseFirestore.getInstance().collection("users").document(
                    id
                )
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        binding.txtAmount.text = document.data?.get("firstName").toString()
                    } else {
                        Log.d("AUTH", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("AUTH", "get failed with ", exception)
                }

        }
    }
}