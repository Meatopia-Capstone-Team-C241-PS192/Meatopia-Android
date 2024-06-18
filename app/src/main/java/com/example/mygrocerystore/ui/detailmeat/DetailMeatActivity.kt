package com.example.mygrocerystore.ui.detailmeat

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mygrocerystore.MainActivity
import com.example.mygrocerystore.R
import com.example.mygrocerystore.databinding.ActivityDetailMeatBinding
import com.example.mygrocerystore.ui.paymentmeat.PaymentActivity

class DetailMeatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMeatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailMeatBinding.inflate(layoutInflater)
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
        binding.buttonBackInDetail.setOnClickListener {
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    companion object {
        const val NAMEMEAT = "name"
        const val PRICEMEAT = "price"
        const val PHOTOMEAT = "photo"
    }
}