package pokitmons.pokit.data.di.network

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import pokitmons.pokit.data.datasource.local.TokenManager
import javax.inject.Inject

// 토큰 api 수정될 때 까지 사용
class BearerTokenInterceptor @Inject constructor(private val tokenManager: TokenManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token: String = runBlocking {
            tokenManager.getAccessToken().first()
        } ?: ""

        val originalRequest: Request = chain.request()
        val requestWithToken: Request = originalRequest.newBuilder()
            .header(
                "Authorization",
                "Bearer $token"
            )
            .build()

        return chain.proceed(requestWithToken)
    }
}
