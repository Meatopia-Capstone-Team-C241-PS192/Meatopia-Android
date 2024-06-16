package com.example.mygrocerystore.data.database

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.mygrocerystore.data.retrofit.ApiConfig
import com.example.mygrocerystore.data.response.LoginResponse
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class Repository(private val application: Application, private val dataPreferences: DataPreferences) {

    fun login(email: String, password: String): LiveData<ThisResult<LoginResponse>> = liveData(Dispatchers.IO) {
        emit(ThisResult.Loading)
        try {
            val emailRequestBody = email.toRequestBody("text/plain".toMediaTypeOrNull())
            val passwordRequestBody = password.toRequestBody("text/plain".toMediaTypeOrNull())
            val response = ApiConfig.getApiService().login(emailRequestBody, passwordRequestBody)
            emit(ThisResult.SuccessData(response))
        } catch (e: Exception) {
            emit(ThisResult.ErrorData(e.message.toString()))
        }
    }
}
