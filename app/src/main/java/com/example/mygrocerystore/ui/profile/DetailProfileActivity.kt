package com.example.mygrocerystore.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mygrocerystore.MainActivity
import com.example.mygrocerystore.R
import com.example.mygrocerystore.databinding.ActivityDetailProfileBinding
import com.example.mygrocerystore.ui.editprofile.EditProfileActivity

class DetailProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailProfileBinding
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
    }

    private fun setUpAction() {
        binding.buttonBackInDetailProfile.setOnClickListener {
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding.buttonEditInDetailProfile.setOnClickListener {
            Intent(this, EditProfileActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}