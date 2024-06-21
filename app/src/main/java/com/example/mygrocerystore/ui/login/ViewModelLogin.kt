package com.example.mygrocerystore.ui.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mygrocerystore.data.database.DataPreferences
import com.example.mygrocerystore.data.database.Repository
import com.example.mygrocerystore.data.database.ThisResult
import com.example.mygrocerystore.data.response.LoginResponse

class ViewModelLogin(
    private val dataPreferences: DataPreferences, private val application: Application
) : ViewModel() {
    private val repositoryLogin = Repository(application, dataPreferences)

    fun loginUser(email: String, password: String): LiveData<ThisResult<LoginResponse>> {
        return repositoryLogin.login(email, password)
    }

}