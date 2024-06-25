package com.example.mygrocerystore.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mygrocerystore.data.model.CartItem
import com.example.mygrocerystore.databinding.ItemMycartBinding
import com.bumptech.glide.Glide

class CartAdapter(private val cartItems: MutableList<CartItem>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(private val binding: ItemMycartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cartItem: CartItem) {
            with(binding) {
                Glide.with(itemView)
                    .load(cartItem.photo)
                    .into(mypPhotoMeat)
                mypKg.text = cartItem.name
                mypPrice.text = "Rp ${cartItem.price}"

                mypCheckbox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        selectedItems.add(cartItem)
                    } else {
                        selectedItems.remove(cartItem)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemMycartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    fun removeSelectedItems() {
        cartItems.removeAll(selectedItems)
        selectedItems.clear()
        notifyDataSetChanged()
    }

    fun getSelectedItems(): List<CartItem> {
        return selectedItems.toList()
    }

    private val selectedItems = mutableSetOf<CartItem>()
}
