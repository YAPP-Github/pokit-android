package pokitmons.pokit.data.datasource.remote.link

import pokitmons.pokit.data.model.link.response.GetLinksResponse
import pokitmons.pokit.domain.model.link.LinksSort

interface LinkDataSource {
    suspend fun getLinks(
        categoryId: Int = 0,
        page: Int = 0,
        size: Int = 10,
        sort: List<String> = listOf(LinksSort.RECENT.value),
        isRead: Boolean = false,
        favorites: Boolean = false,
        startDate: String? = null,
        endDate: String? = null,
        categoryIds: List<Int>? = null,
    ): GetLinksResponse
}
