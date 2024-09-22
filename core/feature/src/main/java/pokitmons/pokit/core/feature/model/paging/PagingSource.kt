package pokitmons.pokit.core.feature.model.paging

interface PagingSource<T> {
    suspend fun load(pageIndex: Int, pageSize: Int): PagingLoadResult<T>
}
