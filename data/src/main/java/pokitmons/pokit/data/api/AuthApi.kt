package pokitmons.pokit.data.api

import pokitmons.pokit.data.model.auth.request.SNSLoginRequest
import pokitmons.pokit.data.model.auth.response.SNSLoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/signin")
    suspend fun snsLogin(
        @Body snsLoginRequest: SNSLoginRequest,
    ): SNSLoginResponse
}
