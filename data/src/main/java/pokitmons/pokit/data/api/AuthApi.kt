package pokitmons.pokit.data.api

import pokitmons.pokit.data.model.auth.request.SNSLoginRequest
import pokitmons.pokit.data.model.auth.request.SignUpRequest
import pokitmons.pokit.data.model.auth.response.DuplicateNicknameResponse
import pokitmons.pokit.data.model.auth.response.SNSLoginResponse
import pokitmons.pokit.data.model.auth.response.SignUpResponse
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

    @POST("user/signup")
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest,
    ): SignUpResponse
}
