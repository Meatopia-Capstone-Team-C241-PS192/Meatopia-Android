package com.example.mygrocerystore.ui.mycart

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygrocerystore.MainActivity
import com.example.mygrocerystore.data.adapter.CartAdapter
import com.example.mygrocerystore.data.database.DataPreferences
import com.example.mygrocerystore.data.model.CartItem
import com.example.mygrocerystore.databinding.ActivityMyCartBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MyCartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyCartBinding
    private val cartItems = mutableListOf<CartItem>()
    private lateinit var cartAdapter: CartAdapter
    private lateinit var pref: DataPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = DataPreferences(this)

        loadCartItems()
        setupRecyclerView()

        // Check for new item
        intent.getStringExtra(NAMEMEAT)?.let { name ->
            val price = intent.getStringExtra(PRICEMEAT) ?: "0"
            val photo = intent.getStringExtra(PHOTOMEAT) ?: ""
            addItemToCart(CartItem(name, price, photo))
        }

        setUpAction()
        updateCheckoutButtonVisibility()
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(cartItems)
        binding.recyclerViewCart.apply {
            layoutManager = LinearLayoutManager(this@MyCartActivity)
            adapter = cartAdapter
        }
    }

    private fun addItemToCart(item: CartItem) {
        cartItems.add(item)
        cartAdapter.notifyItemInserted(cartItems.size - 1)
        saveCartItems() // Save cart items to SharedPreferences
        updateCheckoutButtonVisibility()
    }

    private fun setUpAction() {
        binding.buttonCheckoutInMyCart.setOnClickListener {
            // Send selected items to WhatsApp
            sendSelectedItemsToWhatsApp()
            // Remove selected items
            cartAdapter.removeSelectedItems()
            saveCartItems() // Save cart items to SharedPreferences after removing items
            updateCheckoutButtonVisibility()
        }

        binding.buttonBackInMyCart.setOnClickListener {
            saveCartItems() // Save cart items before leaving the activity
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun sendSelectedItemsToWhatsApp() {
        val selectedItems = cartAdapter.getSelectedItems()
        if (selectedItems.isNotEmpty()) {
            val userName = pref.getUser()?.name
            val phoneNumber = "6285806190296"
            val message = StringBuilder("Halo, saya $userName ingin memesan produk berikut:\n")
            var totalPrice = 0

            selectedItems.forEach { item ->
                message.append("${item.name} - Rp ${item.price}\n")
                totalPrice += item.price.toInt()
            }
            message.append("Dengan Total Harga Rp.$totalPrice")

            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(
                    "https://api.whatsapp.com/send?phone=$phoneNumber&text=${
                        Uri.encode(message.toString())
                    }"
                )
            }
            startActivity(intent)
        }
    }

    private fun updateCheckoutButtonVisibility() {
        if (cartItems.isEmpty()) {
            binding.buttonCheckoutInMyCart.visibility = android.view.View.GONE
        } else {
            binding.buttonCheckoutInMyCart.visibility = android.view.View.VISIBLE
        }
    }

    private fun saveCartItems() {
        val sharedPreferences = getSharedPreferences("MyCartPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(cartItems)
        editor.putString("cartItems", json)
        editor.apply()
    }

    private fun loadCartItems() {
        val sharedPreferences = getSharedPreferences("MyCartPrefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("cartItems", null)
        val type = object : TypeToken<MutableList<CartItem>>() {}.type
        if (json != null) {
            val items: MutableList<CartItem> = gson.fromJson(json, type)
            cartItems.addAll(items)
        }
    }

    companion object {
        const val NAMEMEAT = "name"
        const val PRICEMEAT = "price"
        const val PHOTOMEAT = "photo"
    }
}
