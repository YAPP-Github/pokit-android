package pokitmons.pokit.data.datasource.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthManager @Inject constructor(
    private val dataStore: DataStore<androidx.datastore.preferences.core.Preferences>,
) {
    companion object {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val AUTH_TYPE = stringPreferencesKey("auth_type")
        val USER_ID = intPreferencesKey("user_id")

        private const val INVALID_USER_ID = -1
    }

    fun getAccessToken(): Flow<String?> {
        return dataStore.data.map { prefs ->
            prefs[ACCESS_TOKEN]
        }
    }

    suspend fun setAuthType(type: String) {
        dataStore.edit { prefs ->
            prefs[AUTH_TYPE] = type
        }
    }

    fun getAuthType(): Flow<String> {
        return dataStore.data.map { prefs ->
            prefs[AUTH_TYPE] ?: ""
        }
    }

    suspend fun saveAccessToken(token: String) {
        dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN] = token
        }
    }

    fun getRefreshToken(): Flow<String?> {
        return dataStore.data.map { prefs ->
            prefs[REFRESH_TOKEN]
        }
    }

    suspend fun saveRefreshToken(token: String) {
        dataStore.edit { prefs ->
            prefs[REFRESH_TOKEN] = token
        }
    }

    fun getUserId(): Flow<Int> {
        return dataStore.data.map { prefs ->
            prefs[USER_ID] ?: INVALID_USER_ID
        }
    }

    suspend fun setUserId(userId: Int) {
        dataStore.edit { prefs ->
            prefs[USER_ID] = userId
        }
    }
}
