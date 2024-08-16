package pokitmons.pokit.domain.repository.link

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.link.Link
import pokitmons.pokit.domain.model.link.LinkCard
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

    suspend fun searchLinks(
        page: Int,
        size: Int,
        sort: List<String>,
        isRead: Boolean,
        favorites: Boolean,
        startDate: String?,
        endDate: String?,
        categoryIds: List<Int>?,
        searchWord: String,
    ): PokitResult<List<Link>>

    suspend fun deleteLink(linkId: Int): PokitResult<Int>

    suspend fun getLink(linkId: Int): PokitResult<Link>

    suspend fun modifyLink(
        linkId: Int,
        data: String,
        title: String,
        categoryId: Int,
        memo: String,
        alertYn: String,
        thumbNail: String,
    ): PokitResult<Int>

    suspend fun createLink(
        data: String,
        title: String,
        categoryId: Int,
        memo: String,
        alertYn: String,
        thumbNail: String,
    ): PokitResult<Int>

    suspend fun setBookmark(linkId: Int, bookmarked: Boolean): PokitResult<Unit>

    suspend fun getLinkCard(url: String): PokitResult<LinkCard>
}
