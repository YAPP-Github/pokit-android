package pokitmons.pokit.domain.usecase.auth

import kotlinx.coroutines.flow.Flow
import pokitmons.pokit.domain.repository.auth.AuthRepository
import javax.inject.Inject

class TokenUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend fun setAccessToken(token: String) {
        authRepository.setAccessToken(token)
    }

    suspend fun setRefreshToken(token: String) {
        authRepository.setRefreshToken(token)
    }

    suspend fun setAuthType(type: String) {
        authRepository.setAuthType(type)
    }

    suspend fun getAuthType(): Flow<String> {
        return authRepository.getAuthType()
    }
}
