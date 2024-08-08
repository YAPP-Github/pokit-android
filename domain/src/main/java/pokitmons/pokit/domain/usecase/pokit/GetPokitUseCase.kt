package pokitmons.pokit.domain.usecase.pokit

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.pokit.Pokit
import pokitmons.pokit.domain.repository.pokit.PokitRepository
import javax.inject.Inject

class GetPokitUseCase @Inject constructor(
    private val repository: PokitRepository
) {
    suspend fun getPokit(pokitId: Int): PokitResult<Pokit> {
        return repository.getPokit(pokitId)
    }
}
