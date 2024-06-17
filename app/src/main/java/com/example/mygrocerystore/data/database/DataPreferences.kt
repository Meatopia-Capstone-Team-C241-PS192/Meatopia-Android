package com.example.mygrocerystore.data.database

import android.content.Context
import com.example.mygrocerystore.data.response.LoginResult

class DataPreferences (context: Context) {
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setLogin(value: LoginResult){
        val editor = preferences.edit()
        editor.putString(NAME, value.name)
        editor.putString(ID, value.id)
        editor.putString(ROLE, value.role)
        editor.apply()

    }

    fun getLogin(): LoginResult {
        val name = preferences.getString(NAME, null)
        val id = preferences.getString(ID, null)
        val role = preferences.getString(ROLE, null)
        return LoginResult(name, id, role)
    }

    fun clearLogin(){
        val editor = preferences.edit().clear()
        editor.apply()
    }
    companion object{
        const val PREFS_NAME = "login_pref"
        const val NAME = "name"
        const val ID = "id"
        const val ROLE = "role"

    }
}