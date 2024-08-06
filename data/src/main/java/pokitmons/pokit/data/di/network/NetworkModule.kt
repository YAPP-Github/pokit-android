package pokitmons.pokit.data.di.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pokitmons.pokit.data.api.AuthApi
import pokitmons.pokit.data.api.SettingApi
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val BASE_URL = "https://pokit.site"
private const val API = "api"
private const val VERSION = "v1"

private const val READ_TIME_OUT = 20000L
private const val WRITE_TIME_OUT = 20000L

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(BearerTokenInterceptor())
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
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
            prettyPrint = true
        }
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
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
    fun provideAuthService(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    fun provideSettingService(retrofit: Retrofit): SettingApi {
        return retrofit.create(SettingApi::class.java)
    }
}
