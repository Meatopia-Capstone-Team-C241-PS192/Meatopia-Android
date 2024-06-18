package com.example.mygrocerystore.data.database

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.mygrocerystore.data.retrofit.ApiConfig
import com.example.mygrocerystore.data.response.LoginResponse

import com.example.mygrocerystore.data.response.RegisterResponse

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


    fun registerUser(name: String, email: String, phone: String, address: String, password: String, passwordConfirmation: String): LiveData<ThisResult<RegisterResponse>> = liveData(Dispatchers.IO) {
        emit(ThisResult.Loading)
        try {
            val response = ApiConfig.getApiService().register(name, email, phone, address, password, passwordConfirmation)
            if (response.success) {
                emit(ThisResult.SuccessData(response))
            } else {
                emit(ThisResult.ErrorData("Registration failed: ${response.message}"))
            }
        } catch (e: Exception) {
            emit(ThisResult.ErrorData(e.message.toString()))
        }
    }
}


