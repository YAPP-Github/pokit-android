package pokitmons.pokit.domain.repository.link

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.link.Link
import pokitmons.pokit.domain.model.link.LinksSort

interface LinkRepository {
    suspend fun getLinks(
        categoryId: Int = 0,
        size: Int = 10,
        page: Int = 0,
        sort: LinksSort = LinksSort.RECENT,
        isRead: Boolean = false,
        favorite: Boolean = false,
        startDate: String? = null,
        endDate: String? = null,
        categoryIds: List<Int>? = null,
    ): PokitResult<List<Link>>
}
