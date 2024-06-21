package com.example.mygrocerystore.ui.mycart

import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.mygrocerystore.MainActivity
import com.example.mygrocerystore.R
import com.example.mygrocerystore.data.database.DataHolder
import com.example.mygrocerystore.data.database.DataPreferences
import com.example.mygrocerystore.databinding.ActivityMyCartBinding
import com.example.mygrocerystore.ui.detailmeat.DetailMeatActivity

class MyCartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyCartBinding
    private lateinit var pref: DataPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMyCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize pref here
        pref = DataPreferences(this)

        setUpAction()
        setUpData()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setUpAction() {
        binding.buttonBackInMyCart.setOnClickListener {
            Intent(this, MainActivity::class.java).apply {
                val options = ActivityOptions.makeCustomAnimation(
                    this@MyCartActivity,
                    R.anim.anim_none,
                    R.anim.anim_none
                )
                startActivity(this, options.toBundle())
            }
        }
        binding.buttonCheckoutInMyCart.setOnClickListener {
            val userName = pref.getUser()?.name
            val price = intent.getStringExtra(PRICEMEAT)
            val nameMeat = intent.getStringExtra(NAMEMEAT)
            val phoneNumber = "6285806190296"
            val message =
                "Halo, saya $userName ingin memesan produk $nameMeat. Dengan Total Harga Rp.$price"
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(
                    "https://api.whatsapp.com/send?phone=$phoneNumber&text=${
                        Uri.encode(message)
                    }"
                )
            }
            startActivity(intent)
        }
    }

    private fun setUpData() {
        val photo = DataHolder.photoMeat
        val namemeat = DataHolder.nameMeat
        val price = DataHolder.priceMeat

        binding.apply {
            Glide.with(this@MyCartActivity)
                .load(photo)
                .into(mypPhotoMeat)
            mypName.text = namemeat
            mypPrice.text = "Rp $price"
        }
    }

    companion object {
        const val NAMEMEAT = "name"
        const val PRICEMEAT = "price"
        const val PHOTOMEAT = "photo"
    }
}
