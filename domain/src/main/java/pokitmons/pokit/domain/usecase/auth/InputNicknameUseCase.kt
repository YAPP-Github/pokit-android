package pokitmons.pokit.domain.usecase.auth

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.auth.DuplicateNicknameResult
import pokitmons.pokit.domain.repository.auth.AuthRepository
import javax.inject.Inject

class InputNicknameUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend fun checkDuplicateNickname(nickname: String): PokitResult<DuplicateNicknameResult> {
        return when (val duplicateResult = authRepository.checkDuplicateNickname(nickname)) {
            is PokitResult.Success -> PokitResult.Success(duplicateResult.result)
            is PokitResult.Error -> PokitResult.Error(duplicateResult.error)
        }
    }
}
