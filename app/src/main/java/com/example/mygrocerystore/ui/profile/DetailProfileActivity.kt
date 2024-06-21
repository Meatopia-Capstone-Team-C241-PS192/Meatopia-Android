package com.example.mygrocerystore.ui.profile

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.mygrocerystore.MainActivity
import com.example.mygrocerystore.R
import com.example.mygrocerystore.data.database.DataPreferences
import com.example.mygrocerystore.data.modelfactory.ModelFactory
import com.example.mygrocerystore.databinding.ActivityDetailProfileBinding

class DetailProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProfileBinding
    private val viewModel: ViewModelDetailProfile by viewModels {
        ModelFactory(application, DataPreferences(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.hide()

        setUpAction()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        observeViewModel()
        viewModel.fetchProfile()
    }

    private fun observeViewModel() {
        viewModel.profile.observe(this, Observer { profile ->
            binding.nameDetailProfile.text = profile.name
            binding.usernameDetailProfile.text = profile.name // Ensure this field is correct
            binding.emailDetailProfile.text = profile.email
            binding.phoneDetailProfile.text = profile.phone
            binding.addressDetailProfile.text = profile.address
        })
    }

    private fun setUpAction() {
        binding.buttonBackInDetailProfile.setOnClickListener {
            Intent(this, MainActivity::class.java).apply {
                val options = ActivityOptions.makeCustomAnimation(
                    this@DetailProfileActivity,
                    R.anim.anim_none,
                    R.anim.anim_none
                )
                startActivity(this, options.toBundle())
            }
        }
    }
}
