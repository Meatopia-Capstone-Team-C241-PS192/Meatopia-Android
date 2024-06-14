package com.example.mygrocerystore.ui.result

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mygrocerystore.MainActivity
import com.example.mygrocerystore.R
import com.example.mygrocerystore.databinding.ActivityResultBinding
import com.example.mygrocerystore.ui.camera.CameraActivity

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAction()

        val supportAction = supportActionBar
        supportAction?.hide()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setUpAction() {
        val imageUri = intent.getStringExtra("IMAGE_URI")?.let { Uri.parse(it) }
        imageUri?.let {
            binding.imageViewResult.setImageURI(it)
        }

        binding.buttonBackInResult.setOnClickListener {
            Intent(this, CameraActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }
    }
}