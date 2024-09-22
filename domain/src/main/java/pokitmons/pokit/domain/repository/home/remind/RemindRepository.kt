package pokitmons.pokit.domain.repository.home.remind

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.home.remind.RemindResult
import pokitmons.pokit.domain.model.pokit.PokitsSort

interface RemindRepository {
    suspend fun getUnReadContents(
        filterUncategorized: Boolean = true,
        size: Int = 10,
        page: Int = 0,
        sort: PokitsSort = PokitsSort.RECENT,
    ): PokitResult<List<RemindResult>>

    suspend fun getTodayContents(
        filterUncategorized: Boolean = true,
        size: Int = 10,
        page: Int = 0,
        sort: PokitsSort = PokitsSort.RECENT,
    ): PokitResult<List<RemindResult>>

    suspend fun getBookmarkContents(
        filterUncategorized: Boolean = true,
        size: Int = 10,
        page: Int = 0,
        sort: PokitsSort = PokitsSort.RECENT,
    ): PokitResult<List<RemindResult>>
}
