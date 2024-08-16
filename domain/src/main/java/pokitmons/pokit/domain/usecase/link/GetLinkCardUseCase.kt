package pokitmons.pokit.domain.usecase.link

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.link.LinkCard
import pokitmons.pokit.domain.repository.link.LinkRepository
import javax.inject.Inject

class GetLinkCardUseCase @Inject constructor(
    private val repository: LinkRepository
) {
    suspend fun getLinkCard(url: String) : PokitResult<LinkCard> {
        return repository.getLinkCard(url = url)
    }
}
