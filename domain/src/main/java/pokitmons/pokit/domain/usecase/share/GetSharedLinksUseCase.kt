package pokitmons.pokit.domain.usecase.share

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.link.Link
import pokitmons.pokit.domain.repository.shared.SharedRepository
import javax.inject.Inject

class GetSharedLinksUseCase @Inject constructor(private val sharedRepository: SharedRepository) {
    suspend fun getSharedLinks(categoryId: Int): PokitResult<List<Link>> {
        return when (val sharedLinks = sharedRepository.getSharedLinks(categoryId)) {
            is PokitResult.Success -> PokitResult.Success(sharedLinks.result)
            is PokitResult.Error -> PokitResult.Error(sharedLinks.error)
        }
    }
}
