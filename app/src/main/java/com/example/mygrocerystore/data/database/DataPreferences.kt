package com.example.mygrocerystore.data.database

import android.content.Context
import android.util.Log
import com.example.mygrocerystore.data.response.LoginResult

class DataPreferences(context: Context) {
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setLogin(value: LoginResult) {
        val editor = preferences.edit()
        Log.d(
            "DataPreferences",
            "setLogin: Saving data - name: ${value.name}, id: ${value.id}, token: ${value.token}, role: ${value.role}, email: ${value.email}"
        )
        editor.putString(NAME, value.name)
        editor.putString(ID, value.id)
        editor.putString(TOKEN, value.token)
        editor.putString(ROLE, value.role)
        editor.putString(EMAIL, value.email)
        editor.apply()
        Log.d("DataPreferences", "setLogin: Data saved successfully")
    }

    fun getUser(): LoginResult? {
        val name = preferences.getString(NAME, null)
        val id = preferences.getString(ID, null)
        val token = preferences.getString(TOKEN, null)
        val role = preferences.getString(ROLE, null)
        val email = preferences.getString(EMAIL, null)
        Log.d(
            "DataPreferences",
            "getUser: Retrieved data - name: $name, id: $id, token: $token, role: $role"
        )
        return if (name != null && id != null && token != null && role != null && email != null) {
            LoginResult(name = name, id = id, token = token, role = role, email = email)
        } else {
            Log.d("DataPreferences", "getUser: Data not found")
            null
        }
    }

    fun clearLogin() {
        val editor = preferences.edit().clear()
        editor.apply()
        Log.d("DataPreferences", "clearLogin: Data cleared successfully")
    }

    companion object {
        const val PREFS_NAME = "login_pref"
        const val NAME = "name"
        const val ID = "id"
        const val ROLE = "role"
        const val TOKEN = "token"
        const val EMAIL = "email"
    }
}
