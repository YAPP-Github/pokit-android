package pokitmons.pokit.domain.repository.auth

import pokitmons.pokit.domain.model.PokitResult
import pokitmons.pokit.domain.model.auth.SNSLogin

interface AuthRepository {
    suspend fun snsLogin(
        authPlatform: String,
        idToken: String,
    ): PokitResult<SNSLogin>
}
