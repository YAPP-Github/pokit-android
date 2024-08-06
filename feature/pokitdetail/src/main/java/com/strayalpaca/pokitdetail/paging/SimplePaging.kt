package com.strayalpaca.pokitdetail.paging

import kotlinx.coroutines.flow.Flow

interface SimplePaging<T> {
    val pagingData : Flow<List<T>>
    suspend fun refresh()
    suspend fun load()
    val pagingState : Flow<SimplePagingState>
    suspend fun modifyItem(item : T)
    suspend fun deleteItem(item : T)
    fun clear()
}
