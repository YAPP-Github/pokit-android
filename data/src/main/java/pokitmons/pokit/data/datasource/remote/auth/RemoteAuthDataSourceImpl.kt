package pokitmons.pokit.data.datasource.remote.auth

import pokitmons.pokit.data.api.AuthApi
import pokitmons.pokit.data.model.auth.request.SNSLoginRequest
import pokitmons.pokit.data.model.auth.request.SignUpRequest
import pokitmons.pokit.data.model.auth.response.DuplicateNicknameResponse
import pokitmons.pokit.data.model.auth.response.SNSLoginResponse
import pokitmons.pokit.data.model.auth.response.SignUpResponse
import javax.inject.Inject

class RemoteAuthDataSourceImpl @Inject constructor(private val authApi: AuthApi) : AuthDataSource {
    override suspend fun snsLogin(snsLoginRequest: SNSLoginRequest): SNSLoginResponse {
        return authApi.snsLogin(snsLoginRequest)
    }
}
