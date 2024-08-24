package pokitmons.pokit.data.datasource.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val dataStore: DataStore<Preferences>) {
    companion object {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val GOOGLE_REFRESH_TOKEN = stringPreferencesKey("google_refresh_token")
        val APPLE_REFRESH_TOKEN = stringPreferencesKey("apple_refresh_token")
    }

    fun getAccessToken(): Flow<String> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preference -> preference[ACCESS_TOKEN] ?: "" }
}
