package com.example.skip

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences = application.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        sharedPreferences.edit() { putString("auth_token", "Bearer $token") }
    }

    fun getToken(): String? {
        return sharedPreferences.getString("auth_token", null)
    }

    fun logout() {
        sharedPreferences.edit() { remove("auth_token") }
    }
}