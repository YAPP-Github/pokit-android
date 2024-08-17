package pokitmons.pokit.data.repository.home.remind

import pokitmons.pokit.data.datasource.remote.home.remind.RemindDataSource
import pokitmons.pokit.data.mapper.home.home.RemindMapper
import pokitmons.pokit.data.model.common.parseErrorResult
import pokitmons.pokit.data.model.home.remind.RemindRequest
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.home.remind.RemindResult
import pokitmons.pokit.domain.model.pokit.PokitsSort
import pokitmons.pokit.domain.repository.home.remind.RemindRepository
import javax.inject.Inject

class RemindRepositoryImpl @Inject constructor(private val remindDataSource: RemindDataSource) : RemindRepository {
    override suspend fun getUnReadContents(
        filterUncategorized: Boolean,
        size: Int,
        page: Int,
        sort: PokitsSort,
    ): PokitResult<List<RemindResult>> {
        return runCatching {
            val response = remindDataSource.getUnreadContents(RemindRequest())
            val remindResponse = RemindMapper.mapperToRemind(response)
            PokitResult.Success(remindResponse)
        }.getOrElse { throwable ->
            parseErrorResult(throwable)
        }
    }

    override suspend fun getTodayContents(
        filterUncategorized: Boolean,
        size: Int,
        page: Int,
        sort: PokitsSort,
    ): PokitResult<List<RemindResult>> {
        return runCatching {
            val response = remindDataSource.getTodayContents(RemindRequest())
            val remindResponse = RemindMapper.mapperToTodayContents(response)
            PokitResult.Success(remindResponse)
        }.getOrElse { throwable ->
            parseErrorResult(throwable)
        }
    }

    override suspend fun getBookmarkContents(
        filterUncategorized: Boolean,
        size: Int,
        page: Int,
        sort: PokitsSort,
    ): PokitResult<List<RemindResult>> {
        return runCatching {
            val response = remindDataSource.getBookmarkContents(RemindRequest())
            val remindResponse = RemindMapper.mapperToRemind(response)
            PokitResult.Success(remindResponse)
        }.getOrElse { throwable ->
            parseErrorResult(throwable)
        }
    }
}
