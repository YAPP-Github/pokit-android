package pokitmons.pokit.data.di.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException

// 토큰 api 수정될 때 까지 사용
class BearerTokenInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val requestWithToken: Request = originalRequest.newBuilder()
            .header("Authorization", "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzIxNjU4MjUxLCJleHAiOjMwMDE3MjE2NTgyNTF9.gw6LZimKLuZJ2y0UV5cgvk3F7o92pkRIDgx-qlD_S7qEI01QAFt9dZDyHADabftI")
            .build()
        return chain.proceed(requestWithToken)
    }
}
