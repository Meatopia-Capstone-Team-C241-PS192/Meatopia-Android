package com.example.mygrocerystore.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class MeatResponse(

	@field:SerializedName("MeatResponse")
	val meatResponse: List<MeatResponseItem?>? = null
) : Parcelable

@Parcelize
data class MeatResponseItem(

	@field:SerializedName("quantityAvailable")
	val quantityAvailable: String? = null,

	@field:SerializedName("price")
	val price: String? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("type")
	val type: String? = null
) : Parcelable
