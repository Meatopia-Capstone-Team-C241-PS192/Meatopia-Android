package com.example.mygrocerystore.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MeatResponseItem(

	@field:SerializedName("quantityAvailable")
	val quantityAvailable: String,

	@field:SerializedName("price")
	val price: String,

	@field:SerializedName("imageUrl")
	val imageUrl: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("type")
	val type: String
) : Parcelable
