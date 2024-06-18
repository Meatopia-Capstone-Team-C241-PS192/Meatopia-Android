package com.example.mygrocerystore.ui.register

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mygrocerystore.data.database.DataPreferences
import com.example.mygrocerystore.data.database.Repository
import com.example.mygrocerystore.data.database.ThisResult
import com.example.mygrocerystore.data.response.RegisterResponse

class ModelRegister(private val dataPreferences: DataPreferences, private val application: Application
) : ViewModel() {
    private val repository = Repository(application, dataPreferences)
    fun registerUser(name: String, email: String, phone: String, address: String, password: String, confirmPassword: String): LiveData<ThisResult<RegisterResponse>> {
        return repository.registerUser(name, email, phone, address, password, confirmPassword)
    }
}
