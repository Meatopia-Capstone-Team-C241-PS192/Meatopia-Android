package com.example.mygrocerystore.ui.register

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.mygrocerystore.data.database.DataPreferences
import com.example.mygrocerystore.data.database.Repository
import com.example.mygrocerystore.data.database.ThisResult

class ViewModelRegister(
    private val dataPreferences: DataPreferences, private val application: Application
) : ViewModel() {
    private val repositoryRegister = Repository(application, dataPreferences)

    fun registerUser(
        name: String,
        email: String,
        phone: String,
        address: String,
        password: String,
        confPassword: String,
        role: String
    ) = liveData {
        emit(ThisResult.Loading)
        val result = repositoryRegister.register(name, email, phone, address, password, confPassword, role)
        emitSource(result)
    }
}
