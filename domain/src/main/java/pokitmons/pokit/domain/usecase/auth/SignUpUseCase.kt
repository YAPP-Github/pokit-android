package pokitmons.pokit.domain.usecase.auth

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.auth.SignUpResult
import pokitmons.pokit.domain.repository.auth.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend fun signUp(
        nickname: String,
        categories: List<String>,
    ): PokitResult<SignUpResult> {
        return when (val signUpResult = authRepository.signUp(nickname, categories)) {
            is PokitResult.Success -> PokitResult.Success(signUpResult.result)
            is PokitResult.Error -> PokitResult.Error(signUpResult.error)
        }
    }
}
