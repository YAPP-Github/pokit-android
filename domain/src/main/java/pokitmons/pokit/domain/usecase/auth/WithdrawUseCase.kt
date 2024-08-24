package pokitmons.pokit.domain.usecase.auth

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.repository.auth.AuthRepository
import javax.inject.Inject

class WithdrawUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend fun withdraw(): PokitResult<Unit> {
        return when (val signUpResult = authRepository.withdraw()) {
            is PokitResult.Success -> PokitResult.Success(Unit)
            is PokitResult.Error -> PokitResult.Error(signUpResult.error)
        }
    }
}
