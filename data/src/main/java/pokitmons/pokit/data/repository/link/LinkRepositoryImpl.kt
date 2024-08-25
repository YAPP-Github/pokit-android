package pokitmons.pokit.data.repository.link

import pokitmons.pokit.data.datasource.remote.link.LinkDataSource
import pokitmons.pokit.data.mapper.link.LinkMapper
import pokitmons.pokit.data.model.common.parseErrorResult
import pokitmons.pokit.data.model.link.request.ModifyLinkRequest
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.link.Link
import pokitmons.pokit.domain.model.link.LinkCard
import pokitmons.pokit.domain.model.link.LinksSort
import pokitmons.pokit.domain.repository.link.LinkRepository
import javax.inject.Inject

class LinkRepositoryImpl @Inject constructor(
    private val dataSource: LinkDataSource,
) : LinkRepository {
    override suspend fun getLinks(
        categoryId: Int,
        size: Int,
        page: Int,
        sort: LinksSort,
        isRead: Boolean?,
        favorite: Boolean?,
        startDate: String?,
        endDate: String?,
        categoryIds: List<Int>?,
    ): PokitResult<List<Link>> {
        return runCatching {
            val response = dataSource.getLinks(
                categoryId = categoryId,
                size = size,
                page = page,
                sort = listOf(sort.value),
                isRead = isRead,
                favorites = favorite,
                startDate = startDate,
                endDate = endDate,
                categoryIds = categoryIds
            )
            val mappedResponse = LinkMapper.mapperToLinks(response)
            PokitResult.Success(mappedResponse)
        }.getOrElse { throwable ->
            parseErrorResult(throwable)
        }
    }

    override suspend fun searchLinks(
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
        return runCatching {
            val response = dataSource.searchLinks(
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
            val mappedResponse = LinkMapper.mapperToLinks(response)
            PokitResult.Success(mappedResponse)
        }.getOrElse { throwable ->
            parseErrorResult(throwable)
        }
    }

    override suspend fun deleteLink(linkId: Int): PokitResult<Int> {
        return runCatching {
            dataSource.deleteLink(linkId)
            PokitResult.Success(linkId)
        }.getOrElse { throwable ->
            parseErrorResult(throwable)
        }
    }

    override suspend fun getLink(linkId: Int): PokitResult<Link> {
        return runCatching {
            val response = dataSource.getLink(linkId)
            val mappedResponse = LinkMapper.mapperToLink(response)
            PokitResult.Success(mappedResponse)
        }.getOrElse { throwable ->
            parseErrorResult(throwable)
        }
    }

    override suspend fun modifyLink(
        linkId: Int,
        data: String,
        title: String,
        categoryId: Int,
        memo: String,
        alertYn: String,
        thumbNail: String,
    ): PokitResult<Link> {
        return runCatching {
            val modifyLinkRequest = ModifyLinkRequest(
                data = data,
                title = title,
                categoryId = categoryId,
                memo = memo,
                alertYn = alertYn,
                thumbNail = thumbNail
            )
            val response = dataSource.modifyLink(contentId = linkId, modifyLinkRequest = modifyLinkRequest)
            val mappedResponse = LinkMapper.mapperToLink(response)
            PokitResult.Success(mappedResponse)
        }.getOrElse { throwable ->
            parseErrorResult(throwable)
        }
    }

    override suspend fun createLink(data: String, title: String, categoryId: Int, memo: String, alertYn: String, thumbNail: String): PokitResult<Link> {
        return runCatching {
            val createLinkRequest = ModifyLinkRequest(
                data = data,
                title = title,
                categoryId = categoryId,
                memo = memo,
                alertYn = alertYn,
                thumbNail = thumbNail
            )
            val response = dataSource.createLink(createLinkRequest = createLinkRequest)
            val mappedResponse = LinkMapper.mapperToLink(response)
            PokitResult.Success(mappedResponse)
        }.getOrElse { throwable ->
            parseErrorResult(throwable)
        }
    }

    override suspend fun setBookmark(linkId: Int, bookmarked: Boolean): PokitResult<Unit> {
        return runCatching {
            dataSource.setBookmark(contentId = linkId, bookmarked = bookmarked)
            PokitResult.Success(Unit)
        }.getOrElse { throwable ->
            parseErrorResult(throwable)
        }
    }

    override suspend fun getLinkCard(url: String): PokitResult<LinkCard> {
        return runCatching {
            val response = dataSource.getLinkCard(url)
            val mappedResponse = LinkCard(
                url = response.url,
                title = response.title,
                thumbnailUrl = response.image
            )
            PokitResult.Success(result = mappedResponse)
        }.getOrElse { throwable ->
            parseErrorResult(throwable)
        }
    }

    override suspend fun getUncategorizedLinks(size: Int, page: Int, sort: LinksSort): PokitResult<List<Link>> {
        return runCatching {
            val response = dataSource.getUncategorizedLinks(size = size, page = page, sort = listOf(sort.value))
            val mappedResponse = LinkMapper.mapperToLinks(response)
            PokitResult.Success(mappedResponse)
        }.getOrElse { throwable ->
            parseErrorResult(throwable)
        }
    }
}
