import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygrocerystore.data.adapter.MeatAdapter
import com.example.mygrocerystore.data.database.DataPreferences
import com.example.mygrocerystore.data.modelfactory.ModelFactory
import com.example.mygrocerystore.databinding.FragmentHomeBinding
import com.example.mygrocerystore.ui.camera.CameraActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var dataPreferences: DataPreferences

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            val intent = Intent(activity, CameraActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val application = requireActivity().application
        dataPreferences = DataPreferences(application)
        val factory = ModelFactory.getInstance(application, dataPreferences)
        homeViewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)

        setupRecyclerView()
        loadUserName()

        binding.buttonScan.setOnClickListener {
            checkCameraPermission()
        }

        binding.inputSearchInHome.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                if (query.isNotEmpty()) {
                    searchMeats(query)
                } else {
                    loadAllMeats()
                }
            }
        })

        loadAllMeats()

        return root
    }

    private fun setupRecyclerView() {
        val meatAdapter = MeatAdapter()
        binding.recyclerViewInHome.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = meatAdapter
        }
    }

    private fun searchMeats(query: String) {
        val meatAdapter = binding.recyclerViewInHome.adapter as MeatAdapter
        lifecycleScope.launch {
            homeViewModel.searchMeats(query).collectLatest { pagingData ->
                meatAdapter.submitData(lifecycle, pagingData)
            }
        }
    }

    private fun loadAllMeats() {
        val meatAdapter = binding.recyclerViewInHome.adapter as MeatAdapter
        homeViewModel.getListMeat.observe(viewLifecycleOwner) { pagingData ->
            meatAdapter.submitData(lifecycle, pagingData)
        }
    }

    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                val intent = Intent(activity, CameraActivity::class.java)
                startActivity(intent)
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA) -> {
                // Display rationale
            }
            else -> {
                requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
            }
        }
    }

    private fun loadUserName() {
        val userName = dataPreferences.getUser()?.name
        binding.nameUserInHome.text = "Hello, ${userName}!" ?: "No Name"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
