package pokitmons.pokit.domain.usecase.auth

import pokitmons.pokit.domain.repository.auth.AuthRepository
import javax.inject.Inject

class TokenUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend fun setAccessToken(token: String) {
        authRepository.setAccessToken(token)
    }

    suspend fun setRefreshToken(token: String) {
        authRepository.setRefreshToken(token)
    }
}
