package pokitmons.pokit.domain.usecase.pokit

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.repository.pokit.PokitRepository
import javax.inject.Inject

class ModifyPokitUseCase @Inject constructor(
    private val repository: PokitRepository,
) {
    suspend fun modifyPokit(
        pokitId : Int,
        name : String,
        imageId : Int
    ): PokitResult<Int> {
        return repository.modifyPokit(pokitId = pokitId, name = name, imageId = imageId)
    }
}
