package pokitmons.pokit.domain.repository.auth

import kotlinx.coroutines.flow.Flow
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.auth.DuplicateNicknameResult
import pokitmons.pokit.domain.model.auth.SNSLoginResult
import pokitmons.pokit.domain.model.auth.SignUpResult

interface AuthRepository {
    suspend fun snsLogin(authPlatform: String, idToken: String): PokitResult<SNSLoginResult>
    suspend fun checkDuplicateNickname(nickname: String): PokitResult<DuplicateNicknameResult>
    suspend fun signUp(nickname: String, categories: List<String>): PokitResult<SignUpResult>
    suspend fun setAccessToken(token: String)
    suspend fun setRefreshToken(token: String)

    // TODO 리팩토링
    suspend fun setAuthType(type: String)
    suspend fun getAuthType(): Flow<String>
}
