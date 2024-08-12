package pokitmons.pokit.domain.usecase.link

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.link.Link
import pokitmons.pokit.domain.repository.link.LinkRepository
import javax.inject.Inject

class GetLinkUseCase @Inject constructor(
    private val repository: LinkRepository
) {
    suspend fun getLink(linkId : Int) : PokitResult<Link> {
        return repository.getLink(linkId)
    }
}
