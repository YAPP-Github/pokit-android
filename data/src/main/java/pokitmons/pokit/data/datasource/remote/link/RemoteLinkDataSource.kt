package pokitmons.pokit.data.datasource.remote.link

import pokitmons.pokit.data.api.LinkApi
import pokitmons.pokit.data.model.link.response.GetLinksResponse
import javax.inject.Inject

class RemoteLinkDataSource @Inject constructor(
    private val linkApi: LinkApi,
) : LinkDataSource {
    override suspend fun getLinks(
        categoryId: Int,
        page: Int,
        size: Int,
        sort: List<String>,
        isRead: Boolean,
        favorites: Boolean,
        startDate: String?,
        endDate: String?,
        categoryIds: List<Int>?,
    ): GetLinksResponse {
        return linkApi.getLinks(
            categoryId = categoryId,
            page = page,
            size = size,
            sort = sort,
            isRead = isRead,
            favorites = favorites,
            startDate = startDate,
            endDate = endDate,
            categoryIds = categoryIds
        )
    }
}
