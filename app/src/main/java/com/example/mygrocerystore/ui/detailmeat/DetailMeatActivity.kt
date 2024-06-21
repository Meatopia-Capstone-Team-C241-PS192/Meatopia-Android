package com.example.mygrocerystore.ui.detailmeat

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
import com.example.mygrocerystore.data.database.DataHolder.nameMeat
import com.example.mygrocerystore.data.database.DataPreferences
import com.example.mygrocerystore.databinding.ActivityDetailMeatBinding
import com.example.mygrocerystore.ui.mycart.MyCartActivity

class DetailMeatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMeatBinding
    private lateinit var pref: DataPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailMeatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAction()
        setUpData()

        pref = DataPreferences(this)

        val actionBar = supportActionBar
        actionBar?.hide()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setUpData() {
        val decs = intent.getStringExtra(DESCRIPTIONMEAT)
        val photo = intent.getStringExtra(PHOTOMEAT)
        val namemeat = intent.getStringExtra(NAMEMEAT)
        val price = intent.getStringExtra(PRICEMEAT)
        val type = intent.getStringExtra(TYPEMEAT)
        val quantity = intent.getStringExtra(QUANTITYMEAT)

        // Update singleton object
        DataHolder.apply {
            nameMeat = namemeat
            priceMeat = price
            photoMeat = photo
        }

        binding.apply {
            Glide.with(this@DetailMeatActivity)
                .load(photo)
                .into(imageView2)
            nameMeat.text = namemeat
            descriptionsInDetail.text = decs
            textView3.text = type
            priceMeatInDetail.text = "Rp $price"
            kgNumber.text = "$quantity Kg"
        }
    }

    private fun setUpAction() {
        binding.buttonBackInDetail.setOnClickListener {
            Intent(this, MainActivity::class.java).apply {
                val options = ActivityOptions.makeCustomAnimation(
                    this@DetailMeatActivity,
                    R.anim.anim_none,
                    R.anim.anim_none
                )
                startActivity(this, options.toBundle())
            }
        }

        binding.buttonBuyInDetail.setOnClickListener {
            val userName = pref.getUser()?.name
            val price = intent.getStringExtra(PRICEMEAT)
            val nameMeat = intent.getStringExtra(NAMEMEAT)
            val phoneNumber = "6285806190296"
            val message = "Halo, saya $userName ingin memesan produk $nameMeat. Dengan Total Harga Rp.$price"
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber&text=${Uri.encode(message)}")
            }
            startActivity(intent)
        }

        binding.buttonAddToCartInDetail.setOnClickListener {
            val price = intent.getStringExtra(PRICEMEAT)
            val nameMeat = intent.getStringExtra(NAMEMEAT)
            val intent = Intent(this, MyCartActivity::class.java).apply {
                putExtra(MyCartActivity.NAMEMEAT, nameMeat)
                putExtra(MyCartActivity.PRICEMEAT, price)
            }
            startActivity(intent)
        }
    }

    companion object {
        const val NAMEMEAT = "name"
        const val PRICEMEAT = "price"
        const val PHOTOMEAT = "photo"
        const val DESCRIPTIONMEAT = "description"
        const val TYPEMEAT = "type"
        const val QUANTITYMEAT = "quantity"
    }
}
