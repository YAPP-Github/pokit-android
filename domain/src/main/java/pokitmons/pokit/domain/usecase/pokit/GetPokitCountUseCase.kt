package pokitmons.pokit.domain.usecase.pokit

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.repository.pokit.PokitRepository
import javax.inject.Inject

class GetPokitCountUseCase @Inject constructor(
    private val repository: PokitRepository
) {
    suspend fun getPokitCount(): PokitResult<Int> {
        return repository.getPokitCount()
    }
}
