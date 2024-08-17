package pokitmons.pokit.data.di.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

// 토큰 api 수정될 때 까지 사용
class BearerTokenInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val requestWithToken: Request = originalRequest.newBuilder()
            .header(
                "Authorization",
                "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxMCIsImlhdCI6MTcyMzY0MzEzOSwiZXhwIjoy" +
                    "MDIzNjQzMTM5fQ.3jJ6rpPCaMKSrmiB3NtQ3_sYH0zbBuoS0GAwX69HCu62-Vk6x--eUu4dhZJTmqlm"
            )
            .build()

        return chain.proceed(requestWithToken)
    }
}
