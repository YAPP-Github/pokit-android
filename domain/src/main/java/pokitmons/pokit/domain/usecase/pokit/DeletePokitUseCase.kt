package pokitmons.pokit.domain.usecase.pokit

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.repository.pokit.PokitRepository
import javax.inject.Inject

class DeletePokitUseCase @Inject constructor(
    private val repository: PokitRepository
) {
    suspend fun deletePokit(pokitId: Int) : PokitResult<Unit> {
        return repository.deletePokit(pokitId)
    }
}
