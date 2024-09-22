package pokitmons.pokit.domain.usecase.link

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.link.Link
import pokitmons.pokit.domain.model.link.LinksSort
import pokitmons.pokit.domain.repository.link.LinkRepository
import javax.inject.Inject

class GetLinksUseCase @Inject constructor(
    private val repository: LinkRepository,
) {
    suspend fun getLinks(
        categoryId: Int = 0,
        size: Int = 10,
        page: Int = 0,
        sort: LinksSort = LinksSort.RECENT,
        isRead: Boolean? = null,
        favorite: Boolean? = null,
        startDate: String? = null,
        endDate: String? = null,
        categoryIds: List<Int>? = null,
    ): PokitResult<List<Link>> {
        return repository.getLinks(
            categoryId = categoryId,
            size = size,
            page = page,
            sort = sort,
            isRead = isRead,
            favorite = favorite,
            startDate = startDate,
            endDate = endDate,
            categoryIds = categoryIds
        )
    }

    suspend fun getUncategorizedLinks(
        size: Int = 10,
        page: Int = 0,
        sort: LinksSort = LinksSort.RECENT,
    ): PokitResult<List<Link>> {
        return repository.getUncategorizedLinks(
            size = size,
            page = page,
            sort = sort
        )
    }
}
