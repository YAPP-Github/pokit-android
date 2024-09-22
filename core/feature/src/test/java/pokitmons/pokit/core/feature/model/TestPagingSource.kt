package pokitmons.pokit.core.feature.model

import kotlinx.coroutines.delay
import pokitmons.pokit.core.feature.model.paging.PagingLoadResult
import pokitmons.pokit.core.feature.model.paging.PagingSource
import kotlin.math.min

class TestPagingSource(
    private val loadTime: Long = 1000L,
    private val totalItemCount: Int = 30,
) : PagingSource<String> {
    override suspend fun load(pageIndex: Int, pageSize: Int): PagingLoadResult<String> {
        delay(loadTime)

        val firstItemCount = pageIndex * pageSize + 1

        if (totalItemCount < firstItemCount) {
            return PagingLoadResult.Success(emptyList())
        }

        val startIndex = pageIndex * pageSize
        val lastIndex = min(((pageIndex + 1) * pageSize), totalItemCount)

        val itemList = (startIndex until lastIndex).map { "${it}번째 아이템" }
        return PagingLoadResult.Success(itemList)
    }
}
