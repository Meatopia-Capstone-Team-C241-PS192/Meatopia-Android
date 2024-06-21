package com.example.mygrocerystore.ui.ordermeat

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mygrocerystore.MainActivity
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

        setUpAction()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setUpAction() {
        binding.buttonBackInOrderHistory.setOnClickListener {
            Intent(this, MainActivity::class.java).apply {
                val options = ActivityOptions.makeCustomAnimation(
                    this@OderHistoryActivity,
                    R.anim.anim_none,
                    R.anim.anim_none
                )
                startActivity(this, options.toBundle())
            }
        }
    }
}