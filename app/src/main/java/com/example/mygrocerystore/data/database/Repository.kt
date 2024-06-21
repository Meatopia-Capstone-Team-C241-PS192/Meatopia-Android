package com.example.mygrocerystore.data.database

import MeatSearchPagingSource
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.mygrocerystore.data.response.LoginRequest
import com.example.mygrocerystore.data.response.LoginResponse
import com.example.mygrocerystore.data.response.LoginResult
import com.example.mygrocerystore.data.response.MeResponse
import com.example.mygrocerystore.data.response.MeatResponseItem
import com.example.mygrocerystore.data.retrofit.ApiConfig
import com.example.mygrocerystore.data.retrofit.ApiService
import com.example.mygrocerystore.data.response.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class Repository(private val application: Application, private val dataPreferences: DataPreferences) {
    private val apiService: ApiService = ApiConfig.getApiService()
    private var meatList: List<MeatResponseItem> = emptyList()

    fun login(email: String, password: String): LiveData<ThisResult<LoginResponse>> =
        liveData(Dispatchers.IO) {
            emit(ThisResult.Loading)
            try {
                val loginRequest = LoginRequest(email, password)
                val response = apiService.login(loginRequest)

                Log.d("Repository", "login: Raw response received - ${response.toString()}")

                emit(ThisResult.SuccessData(response))

                response?.let {
                    Log.d(
                        "Repository",
                        "login: response - id: ${it.id}, name: ${it.name}, email: ${it.email}, role: ${it.role}, token: ${it.token}"
                    )
                    val loginResult = LoginResult(
                        role = it.role,
                        name = it.name,
                        email = it.email,
                        id = it.id,
                        token = it.token
                    )
                    dataPreferences.setLogin(loginResult)
                } ?: run {
                    Log.d("Repository", "login: response is null")
                }
            } catch (e: Exception) {
                emit(ThisResult.ErrorData(e.message.toString()))
                Log.e("Repository", "login: Error - ${e.message}")
            }
        }

    fun listMeat(): LiveData<PagingData<MeatResponseItem>> {
        Log.d("Repository", "listMeat: called")
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                MeatPage(dataPreferences, apiService)
            }
        ).liveData
    }

    fun getProfile(token: String): LiveData<ThisResult<MeResponse>> = liveData(Dispatchers.IO) {
        emit(ThisResult.Loading)
        try {
            val bearerToken = "Bearer $token"
            Log.d("Repository", "getProfile: Using token - $bearerToken")
            val response = apiService.getProfile(bearerToken)
            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("Repository", "getProfile: Success - $it")
                    emit(ThisResult.SuccessData(it))
                } ?: run {
                    emit(ThisResult.ErrorData("Response body is null"))
                    Log.e("Repository", "getProfile: Response body is null")
                }
            } else {
                emit(ThisResult.ErrorData("HTTP ${response.code()} ${response.message()}"))
                Log.e("Repository", "getProfile: Error - HTTP ${response.code()} ${response.message()}")
            }
        } catch (e: HttpException) {
            emit(ThisResult.ErrorData("HTTP ${e.code()} ${e.message()}"))
            Log.e("Repository", "getProfile: HttpException - HTTP ${e.code()} ${e.message()}")
        } catch (e: Exception) {
            emit(ThisResult.ErrorData(e.message.toString()))
            Log.e("Repository", "getProfile: Exception - ${e.message}")
        }
    }



    fun searchMeats(query: String): Flow<PagingData<MeatResponseItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MeatSearchPagingSource(apiService, dataPreferences, query) }
        ).flow
    }

    fun registerUser(
        name: String,
        email: String,
        phone: String,
        address: String,
        password: String,
        passwordConfirmation: String
    ): LiveData<ThisResult<RegisterResponse>> = liveData(Dispatchers.IO) {
        emit(ThisResult.Loading)
        try {
            val response = ApiConfig.getApiService()
                .register(name, email, phone, address, password, passwordConfirmation)
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



