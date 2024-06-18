package com.example.mygrocerystore.ui.home

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygrocerystore.data.adapter.MeatAdapter
import com.example.mygrocerystore.data.database.DataPreferences
import com.example.mygrocerystore.data.modelfactory.ModelFactory
import com.example.mygrocerystore.databinding.FragmentHomeBinding
import com.example.mygrocerystore.ui.camera.CameraActivity

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                val intent = Intent(activity, CameraActivity::class.java)
                startActivity(intent)
            } else {
                // Handle permission denial
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val application = requireActivity().application
        val dataPreferences = DataPreferences(application)
        val factory = ModelFactory.getInstance(application, dataPreferences)
        homeViewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)

        setupRecyclerView()


        binding.buttonScan.setOnClickListener {
            checkCameraPermission()
        }

        return root
    }

    private fun setupRecyclerView() {
        val meatAdapter = MeatAdapter()
        homeViewModel.getListMeat.observe(viewLifecycleOwner) { pagingData ->
            Log.d("HomeFragment", "Paging data loaded: $pagingData")
            meatAdapter.submitData(lifecycle, pagingData)
        }
        binding.recyclerViewInHome.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = meatAdapter
        }
    }

    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                val intent = Intent(activity, CameraActivity::class.java)
                startActivity(intent)
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA) -> {
            }
            else -> {
                requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
