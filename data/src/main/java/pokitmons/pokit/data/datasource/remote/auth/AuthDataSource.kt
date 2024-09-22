package pokitmons.pokit.data.datasource.remote.auth

import pokitmons.pokit.data.model.auth.request.SNSLoginRequest
import pokitmons.pokit.data.model.auth.request.SignUpRequest
import pokitmons.pokit.data.model.auth.request.WithdrawRequest
import pokitmons.pokit.data.model.auth.response.DuplicateNicknameResponse
import pokitmons.pokit.data.model.auth.response.SNSLoginResponse
import pokitmons.pokit.data.model.auth.response.SignUpResponse
import retrofit2.Response

interface AuthDataSource {
    suspend fun signUp(signUpRequest: SignUpRequest): SignUpResponse
    suspend fun snsLogin(snsLoginRequest: SNSLoginRequest): SNSLoginResponse
    suspend fun checkDuplicateNickname(nickname: String): DuplicateNicknameResponse
    suspend fun withdraw(withdrawRequest: WithdrawRequest): Response<Unit>
}
