package com.example.mygrocerystore.ui.profile

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.mygrocerystore.data.database.DataPreferences
import com.example.mygrocerystore.data.database.Repository
import com.example.mygrocerystore.data.response.MeResponse
import com.example.mygrocerystore.data.database.ThisResult
import kotlinx.coroutines.launch

class ViewModelDetailProfile(
    application: Application,
    private val repository: Repository,
    private val dataPreferences: DataPreferences
) : AndroidViewModel(application) {

    private val _profile = MutableLiveData<MeResponse>()
    val profile: LiveData<MeResponse> get() = _profile

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchProfile() {
        viewModelScope.launch {
            val token = dataPreferences.getUser()?.token?.trim()
            if (token.isNullOrEmpty()) {
                _error.postValue("Token not found")
                return@launch
            }
            Log.d("ViewModelDetailProfile", "fetchProfile: Retrieved token - $token")

            repository.getProfile(token).observeForever { result ->
                Log.d("ViewModelDetailProfile", "fetchProfile: Result received - $result")
                when (result) {
                    is ThisResult.Loading -> {
                        Log.d("ViewModelDetailProfile", "fetchProfile: Loading")
                        // Optionally handle loading state
                    }
                    is ThisResult.SuccessData -> {
                        Log.d("ViewModelDetailProfile", "fetchProfile: Success - ${result.data}")
                        _profile.postValue(result.data)
                    }
                    is ThisResult.ErrorData -> {
                        val errorMessage = result.data ?: "Unknown error"
                        Log.e("ViewModelDetailProfile", "fetchProfile: Error - $errorMessage")
                        _error.postValue(errorMessage)
                    }
                }
            }
        }
    }
}
