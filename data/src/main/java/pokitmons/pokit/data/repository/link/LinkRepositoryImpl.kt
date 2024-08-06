package pokitmons.pokit.data.repository.link

import pokitmons.pokit.data.datasource.remote.link.LinkDataSource
import pokitmons.pokit.data.mapper.link.LinkMapper
import pokitmons.pokit.data.model.common.parseErrorResult
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.link.Link
import pokitmons.pokit.domain.model.link.LinksSort
import pokitmons.pokit.domain.repository.link.LinkRepository
import javax.inject.Inject

class LinkRepositoryImpl @Inject constructor(
    private val dataSource: LinkDataSource
) : LinkRepository {
    override suspend fun getLinks(categoryId: Int, size: Int, page: Int, sort: LinksSort): PokitResult<List<Link>> {
        return kotlin.runCatching {
            val response = dataSource.getLinks(categoryId =categoryId, size = size, page= page, sort = listOf(sort.value))
            val mappedResponse = LinkMapper.mapperToLinks(response)
            PokitResult.Success(mappedResponse)
        }.getOrElse { throwable ->
            println("<><> ${throwable.message}")
            parseErrorResult(throwable)
        }
    }
}
