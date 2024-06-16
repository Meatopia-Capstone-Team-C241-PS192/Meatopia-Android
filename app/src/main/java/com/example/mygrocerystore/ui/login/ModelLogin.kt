package com.example.mygrocerystore.ui.login

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.mygrocerystore.data.database.DataPreferences
import com.example.mygrocerystore.data.database.Repository

class ModelLogin (
    private val dataPreferences: DataPreferences, private val application: Application
) : ViewModel(){
    private val repositoryLogin = Repository(application, dataPreferences)

    fun loginUser(email: String, password: String) = repositoryLogin.login(email, password)
}