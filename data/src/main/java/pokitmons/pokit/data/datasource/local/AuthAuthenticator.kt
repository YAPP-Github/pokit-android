package pokitmons.pokit.data.datasource.local

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import pokitmons.pokit.data.api.TokenApi
import pokitmons.pokit.data.model.auth.request.TokenRequest
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val authManager: AuthManager,
    private val tokenApi: TokenApi,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request {
        val refreshToken = runBlocking {
            authManager.getRefreshToken().first()
        }

        val accessToken = runBlocking {
            tokenApi.reissue(TokenRequest(refreshToken ?: "")).also { response ->
                authManager.saveAccessToken(response.accessToken)
            }
        }
        return newRequestWithToken(accessToken.accessToken, response.request)
    }

    private fun newRequestWithToken(token: String, request: Request): Request =
        request.newBuilder()
            .header(
                "Authorization",
                "Bearer $token"
            )
            .build()
}
