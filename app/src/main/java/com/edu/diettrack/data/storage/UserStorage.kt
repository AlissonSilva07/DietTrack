package com.edu.diettrack.data.storage

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserStorage @Inject constructor(@ApplicationContext context: Context) {

    private val Context.dataStore by preferencesDataStore("user_prefs")
    private val dataStore = context.dataStore

    object Keys {
        val UID = stringPreferencesKey("user_uid")
        val ONBOARDED = booleanPreferencesKey("user_onboarded")
    }

    val uidFlow: Flow<String?> = dataStore.data.map { prefs ->
        prefs[Keys.UID]
    }

    val onboarded: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[Keys.ONBOARDED] ?: false
    }

    suspend fun saveUid(uid: String) {
        dataStore.edit { prefs -> prefs[Keys.UID] = uid }
    }

    suspend fun clearUid() {
        dataStore.edit { prefs -> prefs.remove(Keys.UID) }
    }

    suspend fun setOnboarded(value: Boolean) {
        dataStore.edit { prefs -> prefs[Keys.ONBOARDED] = value }
    }

    suspend fun clearOnboarded() {
        dataStore.edit { prefs -> prefs.remove(Keys.ONBOARDED) }
    }
}
