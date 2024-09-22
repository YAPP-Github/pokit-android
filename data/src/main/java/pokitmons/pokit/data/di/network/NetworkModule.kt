package pokitmons.pokit.data.di.network

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pokitmons.pokit.data.api.AlertApi
import pokitmons.pokit.data.api.AuthApi
import pokitmons.pokit.data.api.LinkApi
import pokitmons.pokit.data.api.PokitApi
import pokitmons.pokit.data.api.RemindApi
import pokitmons.pokit.data.api.SettingApi
import pokitmons.pokit.data.api.TokenApi
import pokitmons.pokit.data.datasource.local.AuthAuthenticator
import pokitmons.pokit.data.datasource.local.TokenManager
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

private const val BASE_URL = "https://pokit.site"
private const val API = "api"
private const val VERSION = "v1"

private const val READ_TIME_OUT = 20000L
private const val WRITE_TIME_OUT = 20000L

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Named("BaseOkHttpClient")
    @Singleton
    @Provides
    fun provideOkHttpClient(
        interceptor: BearerTokenInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
            .build()
    }

    @Named("ReissueOkHttpClient")
    @Singleton
    @Provides
    fun provideReissueOkHttpClient(
        authAuthenticator: AuthAuthenticator,
        interceptor: BearerTokenInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .authenticator(authAuthenticator)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideTokenManager(dataStore: DataStore<Preferences>): TokenManager {
        return TokenManager(dataStore)
    }

    @Singleton
    @Provides
    fun provideAuthenticator(
        tokenManager: TokenManager,
        tokenApi: TokenApi,
    ): AuthAuthenticator {
        return AuthAuthenticator(tokenManager, tokenApi)
    }

    @Singleton
    @Provides
    fun provideInterceptor(tokenManager: TokenManager): BearerTokenInterceptor {
        return BearerTokenInterceptor(tokenManager)
    }

    @Singleton
    @Provides
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
            prettyPrint = true
        }
    }

    @Named("ReissueRetrofit")
    @Singleton
    @Provides
    fun provideReissueRetrofit(
        @Named("ReissueOkHttpClient") okHttpClient: OkHttpClient,
        json: Json,
    ): Retrofit {
        val converterFactory = json.asConverterFactory("application/json; charset=UTF8".toMediaType())
        return Retrofit.Builder()
            .baseUrl("$BASE_URL/$API/$VERSION/")
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
    }

    @Named("BaseRetrofit")
    @Singleton
    @Provides
    fun provideRetrofit(
        @Named("BaseOkHttpClient") okHttpClient: OkHttpClient,
        json: Json,
    ): Retrofit {
        val converterFactory = json.asConverterFactory("application/json; charset=UTF8".toMediaType())
        return Retrofit.Builder()
            .baseUrl("$BASE_URL/$API/$VERSION/")
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideAuthService(@Named("BaseRetrofit") retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    fun provideTokenService(@Named("ReissueRetrofit") retrofit: Retrofit): TokenApi {
        return retrofit.create(TokenApi::class.java)
    }

    @Provides
    fun providePokitService(@Named("BaseRetrofit") retrofit: Retrofit): PokitApi {
        return retrofit.create(PokitApi::class.java)
    }

    @Provides
    fun provideLinkService(@Named("BaseRetrofit") retrofit: Retrofit): LinkApi {
        return retrofit.create(LinkApi::class.java)
    }

    @Provides
    fun provideSettingService(@Named("BaseRetrofit") retrofit: Retrofit): SettingApi {
        return retrofit.create(SettingApi::class.java)
    }

    @Provides
    fun provideRemindService(@Named("BaseRetrofit") retrofit: Retrofit): RemindApi {
        return retrofit.create(RemindApi::class.java)
    }

    @Provides
    fun provideAlertService(@Named("BaseRetrofit") retrofit: Retrofit): AlertApi {
        return retrofit.create(AlertApi::class.java)
    }
}
