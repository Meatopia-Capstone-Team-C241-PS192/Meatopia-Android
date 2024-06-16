package com.example.mygrocerystore.data.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mygrocerystore.data.response.MeatResponseItem
import com.example.mygrocerystore.databinding.ItemMyproductBinding
import com.example.mygrocerystore.ui.detailmeat.DetailMeatActivity

class MeatAdapter : PagingDataAdapter<MeatResponseItem, MeatAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        return ListViewHolder(
            ItemMyproductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(meat = data)
        }
    }

    class ListViewHolder(private val binding: ItemMyproductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meat: MeatResponseItem) {
            with(binding) {
                Glide.with(itemView)
                    .load(meat.imageUrl)
                    .into(mycPhotoMeat)
                mycNameMeat.text = meat.name
                mycPrice.text = meat.price
                binding.cardMeat.setOnClickListener {
                    val intent = Intent(itemView.context, DetailMeatActivity::class.java).apply {
                        putExtra(DetailMeatActivity.NAMEMEAT, meat.name)
                        putExtra(DetailMeatActivity.PRICEMEAT, meat.description)
                        putExtra(DetailMeatActivity.PHOTOMEAT, meat.imageUrl)
                    }
                    val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        androidx.core.util.Pair(mycPhotoMeat as View, "images"),
                        androidx.core.util.Pair(mycNameMeat as View, "name")
                    )
                    itemView.context.startActivity(intent, optionsCompat.toBundle())
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MeatResponseItem>() {
            override fun areItemsTheSame(oldItem: MeatResponseItem, newItem: MeatResponseItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: MeatResponseItem,
                newItem: MeatResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
