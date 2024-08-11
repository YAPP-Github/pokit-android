package pokitmons.pokit.domain.usecase.pokit

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.pokit.Pokit
import pokitmons.pokit.domain.repository.pokit.PokitRepository
import javax.inject.Inject

class GetPokitImagesUseCase @Inject constructor(
    private val repository: PokitRepository,
) {
    suspend fun getImages(): PokitResult<List<Pokit.Image>> {
        return repository.getPokitImages()
    }
}
