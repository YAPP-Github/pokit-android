package pokitmons.pokit.domain.usecase.auth

import pokitmons.pokit.domain.model.PokitResult
import pokitmons.pokit.domain.model.auth.SNSLogin
import pokitmons.pokit.domain.repository.auth.AuthRepository
import javax.inject.Inject

class SNSLoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend fun snsLogin(authPlatform: String, idToken: String): PokitResult<SNSLogin> {
        return when (val loginResult = authRepository.snsLogin(authPlatform, idToken)) {
            is PokitResult.Success -> PokitResult.Success(loginResult.result)
            is PokitResult.Error -> PokitResult.Error(loginResult.error)
        }
    }
}
