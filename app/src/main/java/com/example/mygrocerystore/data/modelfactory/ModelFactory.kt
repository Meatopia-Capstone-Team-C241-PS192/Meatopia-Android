package com.example.mygrocerystore.data.modelfactory

import HomeViewModel
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mygrocerystore.data.database.DataPreferences
import com.example.mygrocerystore.data.database.Repository
import com.example.mygrocerystore.ui.login.ViewModelLogin
import com.example.mygrocerystore.ui.profile.ViewModelDetailProfile
import com.example.mygrocerystore.ui.register.ViewModelRegister

class ModelFactory(
    private val application: Application,
    private val dataPreferences: DataPreferences
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = Repository(application, dataPreferences)
        return when {
            modelClass.isAssignableFrom(ViewModelLogin::class.java) ->
                ViewModelLogin(dataPreferences, application) as T

            modelClass.isAssignableFrom(HomeViewModel::class.java) ->
                HomeViewModel(dataPreferences, application) as T

            modelClass.isAssignableFrom(ViewModelRegister::class.java) ->
                ViewModelRegister(dataPreferences, application) as T

            modelClass.isAssignableFrom(ViewModelDetailProfile::class.java) ->
                ViewModelDetailProfile(application, repository, dataPreferences) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application, dataPreferences: DataPreferences): ModelFactory {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ModelFactory(application, dataPreferences).also { INSTANCE = it }
            }
        }
    }
}
