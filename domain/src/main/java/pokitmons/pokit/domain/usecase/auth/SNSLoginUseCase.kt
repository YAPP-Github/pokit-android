package pokitmons.pokit.domain.usecase.auth

import pokitmons.pokit.domain.model.auth.SNSLogin
import pokitmons.pokit.domain.repository.auth.AuthRepository
import javax.inject.Inject

class SNSLoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend fun snsLogin(authPlatform: String, idToken: String): Result<SNSLogin> {
        return authRepository.snsLogin(authPlatform, idToken)
    }
}
