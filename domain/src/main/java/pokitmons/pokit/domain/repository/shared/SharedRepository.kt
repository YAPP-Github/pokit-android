package pokitmons.pokit.domain.repository.shared

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.link.Link
import pokitmons.pokit.domain.model.link.LinksSort

interface SharedRepository {
    suspend fun getSharedLinks(
        categoryId: Int = 0,
        size: Int = 10,
        page: Int = 0,
        sort: LinksSort = LinksSort.RECENT
    ): PokitResult<List<Link>>
}
