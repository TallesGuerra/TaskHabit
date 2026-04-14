package com.example.taskhabit.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

data class UserProfile(
    val name: String = "",
    val email: String = "",
    val birthDate: String = "",
    val language: String = "en"
)

class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val KEY_NAME = stringPreferencesKey("user_name")
        val KEY_EMAIL = stringPreferencesKey("user_email")
        val KEY_BIRTH_DATE = stringPreferencesKey("user_birth_date")
        val KEY_LANGUAGE = stringPreferencesKey("user_language")
    }

    val userProfile: Flow<UserProfile> = dataStore.data.map { prefs ->
        UserProfile(
            name = prefs[KEY_NAME] ?: "",
            email = prefs[KEY_EMAIL] ?: "",
            birthDate = prefs[KEY_BIRTH_DATE] ?: "",
            language = prefs[KEY_LANGUAGE] ?: "en"
        )
    }

    suspend fun saveProfile(profile: UserProfile) {
        dataStore.edit { prefs ->
            prefs[KEY_NAME] = profile.name
            prefs[KEY_EMAIL] = profile.email
            prefs[KEY_BIRTH_DATE] = profile.birthDate
            prefs[KEY_LANGUAGE] = profile.language
        }
    }

    suspend fun setLanguage(language: String) {
        dataStore.edit { prefs ->
            prefs[KEY_LANGUAGE] = language
        }
    }
}
