package pokitmons.pokit.data.repository.pokit

import pokitmons.pokit.data.datasource.remote.pokit.PokitDataSource
import pokitmons.pokit.data.mapper.pokit.PokitMapper
import pokitmons.pokit.data.model.common.parseErrorResult
import pokitmons.pokit.data.model.pokit.request.CreatePokitRequest
import pokitmons.pokit.data.model.pokit.request.GetPokitsRequest
import pokitmons.pokit.data.model.pokit.request.ModifyPokitRequest
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

    override suspend fun createPokit(name: String, imageId: Int): PokitResult<Int> {
        return runCatching {
            val request = CreatePokitRequest(categoryName = name, categoryImageId = imageId)
            val response = pokitDataSource.createPokit(request)
            val pokitId = response.categoryId
            PokitResult.Success(pokitId)
        }.getOrElse { throwable ->
            parseErrorResult(throwable)
        }
    }

    override suspend fun modifyPokit(pokitId: Int, name: String, imageId: Int): PokitResult<Int> {
        return runCatching {
            val request = ModifyPokitRequest(categoryName = name, categoryImageId = imageId)
            val response = pokitDataSource.modifyPokit(pokitId, request)
            PokitResult.Success(response.categoryId)
        }.getOrElse { throwable ->
            parseErrorResult(throwable)
        }
    }

    override suspend fun getPokitImages(): PokitResult<List<Pokit.Image>> {
        return runCatching {
            val response = pokitDataSource.getPokitImages()
            val mappedResponse = PokitMapper.mapperToPokitImages(response)
            PokitResult.Success(mappedResponse)
        }.getOrElse { throwable ->
            parseErrorResult(throwable)
        }
    }

    override suspend fun getPokit(pokitId: Int): PokitResult<Pokit> {
        return runCatching {
            val response = pokitDataSource.getPokit(pokitId)
            val mappedResponse = PokitMapper.mapperToPokit(response)
            PokitResult.Success(mappedResponse)
        }.getOrElse { throwable ->
            parseErrorResult(throwable)
        }
    }

    override suspend fun deletePokit(pokitId: Int): PokitResult<Unit> {
        return runCatching {
            pokitDataSource.deletePokit(pokitId)
            PokitResult.Success(result = Unit)
        }.getOrElse { throwable ->
            parseErrorResult(throwable)
        }
    }

    override suspend fun getPokitCount(): PokitResult<Int> {
        return kotlin.runCatching {
            val response = pokitDataSource.getPokitCount()
            PokitResult.Success(result = response.categoryTotalCount)
        }.getOrElse { throwable ->
            parseErrorResult(throwable)
        }
    }
}
