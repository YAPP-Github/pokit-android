package pokitmons.pokit.domain.usecase.link

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.repository.link.LinkRepository
import javax.inject.Inject

class DeleteLinkUseCase @Inject constructor(
    private val repository: LinkRepository
) {
    suspend fun deleteLink(linkId : Int) : PokitResult<Int> {
        return repository.deleteLink(linkId)
    }
}
