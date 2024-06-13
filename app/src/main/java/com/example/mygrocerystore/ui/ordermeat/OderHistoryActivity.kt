package com.example.mygrocerystore.ui.ordermeat

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mygrocerystore.R
import com.example.mygrocerystore.databinding.ActivityOderHistoryBinding

class OderHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOderHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = supportActionBar
        toolbar?.hide()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}