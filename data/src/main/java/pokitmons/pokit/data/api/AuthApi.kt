package pokitmons.pokit.data.api

import pokitmons.pokit.data.model.auth.request.SNSLoginRequest
import pokitmons.pokit.data.model.auth.response.DuplicateNicknameResponse
import pokitmons.pokit.data.model.auth.response.SNSLoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthApi {
    @POST("auth/signin")
    suspend fun snsLogin(
        @Body snsLoginRequest: SNSLoginRequest,
    ): SNSLoginResponse

    @GET("user/duplicate/{nickname}")
    suspend fun checkDuplicateNickname(
        @Path(value = "nickname") nickname: String,
    ): DuplicateNicknameResponse
}
