package pokitmons.pokit.data.repository.pokit

import pokitmons.pokit.data.datasource.remote.pokit.PokitDataSource
import pokitmons.pokit.data.mapper.pokit.PokitMapper
import pokitmons.pokit.data.model.common.parseErrorResult
import pokitmons.pokit.data.model.pokit.request.GetPokitsRequest
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.pokit.Pokit
import pokitmons.pokit.domain.model.pokit.PokitsSort
import pokitmons.pokit.domain.repository.pokit.PokitRepository
import javax.inject.Inject

class PokitRepositoryImpl @Inject constructor(
    private val pokitDataSource: PokitDataSource,
) : PokitRepository {
    override suspend fun getPokits(
        filterUncategorized: Boolean,
        size: Int,
        page: Int,
        sort: PokitsSort
    ): PokitResult<List<Pokit>> {
        return runCatching {
            val request = GetPokitsRequest(
                filterUncategoriezd = filterUncategorized,
                size = size,
                page = page,
                sort = sort
            )
            val response = pokitDataSource.getPokits(request)
            val mappedResponse = PokitMapper.mapperToPokits(response)
            PokitResult.Success(mappedResponse)
        }.getOrElse { throwable ->
            parseErrorResult(throwable)
        }
    }
}
