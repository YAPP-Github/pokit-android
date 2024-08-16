package pokitmons.pokit.domain.usecase.link

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.repository.link.LinkRepository
import javax.inject.Inject

class SetBookmarkUseCase @Inject constructor(
    private val repository: LinkRepository,
) {
    suspend fun setBookMarked(linkId: Int, bookmarked: Boolean): PokitResult<Unit> {
        return repository.setBookmark(linkId = linkId, bookmarked = bookmarked)
    }
}
