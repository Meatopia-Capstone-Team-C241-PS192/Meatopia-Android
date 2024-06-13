package com.example.mygrocerystore.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mygrocerystore.MainActivity
import com.example.mygrocerystore.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding!!.root

        val textView = binding!!.textHome
        homeViewModel.text.observe(viewLifecycleOwner) { text: CharSequence? ->
            textView.text = text
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Hide the support action bar
        (activity as? MainActivity)?.hideSupportActionBar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        // Show the support action bar
        (activity as? MainActivity)?.showSupportActionBar()
    }
}