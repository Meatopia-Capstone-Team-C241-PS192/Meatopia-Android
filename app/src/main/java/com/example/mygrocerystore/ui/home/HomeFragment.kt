package com.example.mygrocerystore.ui.home

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mygrocerystore.databinding.FragmentHomeBinding
import com.example.mygrocerystore.ui.camera.CameraActivity

class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null

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
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding!!.root

        val textView = binding!!.nameUserInHome
        homeViewModel.text.observe(viewLifecycleOwner) { text: CharSequence? ->
            textView.text = text
        }

        binding!!.buttonScan.setOnClickListener {
            checkCameraPermission()
        }
        return root
    }

    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                // Permission is granted
                val intent = Intent(activity, CameraActivity::class.java)
                startActivity(intent)
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA) -> {
                // Provide an additional rationale to the user
                // This would be a good place to show a dialog explaining why the permission is needed
            }
            else -> {
                // Directly request the permission
                requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}