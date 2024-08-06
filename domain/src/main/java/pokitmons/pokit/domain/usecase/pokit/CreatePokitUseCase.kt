package pokitmons.pokit.domain.usecase.pokit

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.repository.pokit.PokitRepository
import javax.inject.Inject

class CreatePokitUseCase @Inject constructor(
    private val repository: PokitRepository,
) {
    suspend fun createPokit(
        name: String,
        imageId: Int,
    ): PokitResult<Int> {
        return repository.createPokit(name = name, imageId = imageId)
    }
}
