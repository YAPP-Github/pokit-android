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

    override suspend fun checkDuplicateNickname(nickname: String): DuplicateNicknameResponse {
        return authApi.checkDuplicateNickname(nickname)
    }

    override suspend fun signUp(signUpRequest: SignUpRequest): SignUpResponse {
        return authApi.signUp(signUpRequest)
    }
}
