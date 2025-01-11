package pokitmons.pokit.data.repository.shared

import pokitmons.pokit.data.datasource.remote.shared.SharedDataSource
import pokitmons.pokit.data.mapper.link.LinkMapper
import pokitmons.pokit.data.model.common.parseErrorResult
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.link.Link
import pokitmons.pokit.domain.model.link.LinksSort
import pokitmons.pokit.domain.repository.shared.SharedRepository
import javax.inject.Inject

class SharedRepositoryImpl @Inject constructor(
    private val sharedDataSource: SharedDataSource
) : SharedRepository {
    override suspend fun getSharedLinks(
        categoryId: Int,
        size: Int,
        page: Int,
        sort: LinksSort
    ): PokitResult<List<Link>> {
        return runCatching {
            val response = sharedDataSource.getSharedPokitContentsPreview(
                categoryId = categoryId,
                size = size,
                page = page,
                sort = listOf(sort.value),
            )
            val mappedResponse = LinkMapper.mapperToLinks(response)
            PokitResult.Success(mappedResponse)
        }.getOrElse { throwable ->
            parseErrorResult(throwable)
        }
    }
}
