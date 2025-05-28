package com.example.base.datasore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_prefs")


class UserPreferences(private val context: Context) {

    companion object {
        val EMAIL_KEY = stringPreferencesKey("user_email")
        val PASSWORD_KEY = stringPreferencesKey("user_password")
    }

    val userEmail: Flow<String?> = context.dataStore.data
        .map { preferences -> preferences[EMAIL_KEY] }

    val userPassword: Flow<String?> = context.dataStore.data
        .map { preferences -> preferences[PASSWORD_KEY] }

    suspend fun saveUserSession(email: String, password: String) {
        context.dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = email
            preferences[PASSWORD_KEY] = password
        }
    }
    suspend fun getEmail(): String? {
        val email = userEmail.first()
        return email
    }

    suspend fun getPassword(): String? {
        val password = userPassword.first()
        return password
    }
    suspend fun clearSession() {
        context.dataStore.edit { it.clear() }
    }
}
