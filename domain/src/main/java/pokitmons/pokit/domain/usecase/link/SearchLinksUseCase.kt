package pokitmons.pokit.domain.usecase.link

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.link.Link
import pokitmons.pokit.domain.repository.link.LinkRepository
import javax.inject.Inject

class SearchLinksUseCase @Inject constructor(
    private val repository: LinkRepository,
) {
    suspend fun searchLinks(
        page: Int,
        size: Int,
        sort: List<String>,
        isRead: Boolean?,
        favorites: Boolean?,
        startDate: String?,
        endDate: String?,
        categoryIds: List<Int>?,
        searchWord: String,
    ): PokitResult<List<Link>> {
        return repository.searchLinks(
            page = page,
            size = size,
            sort = sort,
            isRead = isRead,
            favorites = favorites,
            startDate = startDate,
            endDate = endDate,
            categoryIds = categoryIds,
            searchWord = searchWord
        )
    }
}
