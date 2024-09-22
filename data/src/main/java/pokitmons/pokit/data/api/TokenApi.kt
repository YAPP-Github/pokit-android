package pokitmons.pokit.data.api

import pokitmons.pokit.data.model.auth.request.TokenRequest
import pokitmons.pokit.data.model.auth.response.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenApi {
    @POST("auth/reissue")
    suspend fun reissue(
        @Body tokenRequest: TokenRequest,
    ): TokenResponse
}
