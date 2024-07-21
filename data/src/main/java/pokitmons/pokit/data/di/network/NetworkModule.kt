package pokitmons.pokit.data.di.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

private const val BASE_URL = "https://pokit.site"
private const val API = "api"
private const val VERSION = "v1"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient {
        return OkHttpClient
            .Builder()
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
        json: Json
    ): Retrofit {
        val converterFactory = json.asConverterFactory("application/json; charset=UTF8".toMediaType())
        return Retrofit.Builder()
            .baseUrl("${BASE_URL}/${API}/${VERSION}/")
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
    }
}
