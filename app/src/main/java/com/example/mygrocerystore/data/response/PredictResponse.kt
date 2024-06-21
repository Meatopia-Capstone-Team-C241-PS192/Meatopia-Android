package com.example.mygrocerystore.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class PredictResponse(

	@field:SerializedName("data")
	val data: Data = Data(),

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class Data(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("confidence")
	val confidence: Double? = null,

	@field:SerializedName("prediction")
	val prediction: String? = null,

	@field:SerializedName("id")
	val id: String? = null
) : Parcelable
