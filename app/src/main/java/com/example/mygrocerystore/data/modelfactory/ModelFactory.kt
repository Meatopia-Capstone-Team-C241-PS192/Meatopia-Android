package com.example.mygrocerystore.data.modelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mygrocerystore.data.database.DataPreferences
import com.example.mygrocerystore.ui.login.ModelLogin

class ModelFactory (
    private val application: Application,
    private val dataPreferences: DataPreferences
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(ModelLogin::class.java) ->
                ModelLogin(
                    dataPreferences,
                    application
                ) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ModelFactory? = null

        @JvmStatic
        fun getInstance(
            application: Application,
            dataPreferences: DataPreferences
        ): ModelFactory {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?:ModelFactory(application, dataPreferences).also { INSTANCE = it }
            }
        }
    }
}
