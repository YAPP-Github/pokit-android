package pokitmons.pokit.data.datasource.remote.auth

import pokitmons.pokit.data.model.auth.request.SNSLoginRequest
import pokitmons.pokit.data.model.auth.response.DuplicateNicknameResponse
import pokitmons.pokit.data.model.auth.response.SNSLoginResponse

interface AuthDataSource {
//    suspend fun signUp(signUpRequest: SignUpRequest): PokitResponse<SignUpResponse>
    suspend fun snsLogin(snsLoginRequest: SNSLoginRequest): SNSLoginResponse
    suspend fun checkDuplicateNickname(nickname: String): DuplicateNicknameResponse
}
