package com.example.mygrocerystore.ui.editprofile

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mygrocerystore.R
import com.example.mygrocerystore.databinding.ActivityEditProfileBinding
import com.example.mygrocerystore.ui.profile.DetailProfileActivity

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAction()

        val actionBar = supportActionBar
        actionBar?.hide()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setUpAction() {
        binding.buttonBackInEditProfile.setOnClickListener {
            Intent(this, DetailProfileActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}