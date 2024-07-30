package pokitmons.pokit.domain.repository.auth

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.auth.DuplicateNicknameResult
import pokitmons.pokit.domain.model.auth.SNSLoginResult

interface AuthRepository {
    suspend fun snsLogin(
        authPlatform: String,
        idToken: String,
    ): PokitResult<SNSLoginResult>

    suspend fun checkDuplicateNickname(nickname: String): PokitResult<DuplicateNicknameResult>
}
