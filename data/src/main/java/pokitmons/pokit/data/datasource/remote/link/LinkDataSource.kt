package pokitmons.pokit.data.datasource.remote.link

import pokitmons.pokit.data.model.link.request.ModifyLinkRequest
import pokitmons.pokit.data.model.link.response.GetLinkResponse
import pokitmons.pokit.data.model.link.response.GetLinksResponse
import pokitmons.pokit.data.model.link.response.LinkCardResponse
import pokitmons.pokit.data.model.link.response.ModifyLinkResponse
import pokitmons.pokit.domain.model.link.LinksSort

interface LinkDataSource {
    suspend fun getLinks(
        categoryId: Int = 0,
        page: Int = 0,
        size: Int = 10,
        sort: List<String> = listOf(LinksSort.RECENT.value),
        isRead: Boolean? = null,
        favorites: Boolean? = null,
        startDate: String? = null,
        endDate: String? = null,
        categoryIds: List<Int>? = null,
    ): GetLinksResponse

    suspend fun searchLinks(
        page: Int = 0,
        size: Int = 10,
        sort: List<String> = listOf(LinksSort.RECENT.value),
        isRead: Boolean = false,
        favorites: Boolean = false,
        startDate: String? = null,
        endDate: String? = null,
        categoryIds: List<Int>? = null,
        searchWord: String = "",
    ): GetLinksResponse

    suspend fun deleteLink(contentId: Int)

    suspend fun getLink(contentId: Int): GetLinkResponse

    suspend fun modifyLink(
        contentId: Int,
        modifyLinkRequest: ModifyLinkRequest,
    ): ModifyLinkResponse

    suspend fun createLink(
        createLinkRequest: ModifyLinkRequest,
    ): ModifyLinkResponse

    suspend fun setBookmark(contentId: Int, bookmarked: Boolean)

    suspend fun getLinkCard(url: String): LinkCardResponse

    suspend fun getUncategorizedLinks(
        page: Int = 0,
        size: Int = 10,
        sort: List<String> = listOf(LinksSort.RECENT.value),
    ): GetLinksResponse
}
