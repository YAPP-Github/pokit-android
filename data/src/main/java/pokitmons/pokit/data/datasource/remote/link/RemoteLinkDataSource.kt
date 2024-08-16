package pokitmons.pokit.data.datasource.remote.link

import org.jsoup.Jsoup
import pokitmons.pokit.data.api.LinkApi
import pokitmons.pokit.data.model.link.request.ModifyLinkRequest
import pokitmons.pokit.data.model.link.response.GetLinkResponse
import pokitmons.pokit.data.model.link.response.GetLinksResponse
import pokitmons.pokit.data.model.link.response.LinkCardResponse
import pokitmons.pokit.data.model.link.response.ModifyLinkResponse
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

    override suspend fun searchLinks(
        page: Int,
        size: Int,
        sort: List<String>,
        isRead: Boolean,
        favorites: Boolean,
        startDate: String?,
        endDate: String?,
        categoryIds: List<Int>?,
        searchWord: String,
    ): GetLinksResponse {
        return linkApi.searchLinks(
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

    override suspend fun deleteLink(contentId: Int) {
        return linkApi.deleteLink(contentId = contentId)
    }

    override suspend fun getLink(contentId: Int): GetLinkResponse {
        return linkApi.getLink(contentId)
    }

    override suspend fun modifyLink(
        contentId: Int,
        modifyLinkRequest: ModifyLinkRequest
    ): ModifyLinkResponse {
        return linkApi.modifyLink(
            contentId = contentId,
            modifyLinkRequest = modifyLinkRequest
        )
    }

    override suspend fun createLink(createLinkRequest: ModifyLinkRequest): ModifyLinkResponse {
        return linkApi.createLink(
            createLinkRequest = createLinkRequest
        )
    }

    override suspend fun setBookmark(contentId: Int, bookmarked: Boolean) {
        if (bookmarked) {
            linkApi.applyBookmark(contentId)
        } else {
            linkApi.cancelBookmark(contentId)
        }
    }

    override suspend fun getLinkCard(url: String): LinkCardResponse {
        val document = Jsoup.connect(url).get()
        val image = document.select("meta[property=og:image]").attr("content").ifEmpty { null }
        val title = document.select("meta[property=og:title]").attr("content")
        return LinkCardResponse(
            url = url,
            image = image,
            title = title
        )
    }
}
