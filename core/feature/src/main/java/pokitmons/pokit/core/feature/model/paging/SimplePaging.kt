package pokitmons.pokit.core.feature.model.paging

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class SimplePaging<ITEM, KEY> (
    private val pagingSource: PagingSource<ITEM>,
    private val getKeyFromItem: (ITEM) -> KEY,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO),
    private val perPage: Int = 10,
    private val initPage: Int = 0,
    private val firstRequestPage: Int = 3,
) {
    private val _pagingData : MutableStateFlow<List<ITEM>> = MutableStateFlow(emptyList())
    val pagingData : StateFlow<List<ITEM>> = _pagingData.asStateFlow()

    private val _pagingState : MutableStateFlow<PagingState> = MutableStateFlow(PagingState.IDLE)
    val pagingState : StateFlow<PagingState> = _pagingState.asStateFlow()

    private var pagingDataRequestJob: Job? = null
    private var currentPageIndex = 0

    suspend fun refresh() {
        pagingDataRequestJob?.cancel()

        _pagingData.update { emptyList() }
        _pagingState.update { PagingState.LOADING_INIT }

        pagingDataRequestJob = coroutineScope.launch {
            try {
                currentPageIndex = initPage

                val response = pagingSource.load(pageIndex = currentPageIndex, pageSize = perPage * firstRequestPage)
                when (response) {
                    is PagingLoadResult.Success -> {
                        val itemList = response.result
                        applyResponse(itemList, firstRequestPage)
                    }
                    is PagingLoadResult.Error -> {
                        _pagingState.update { PagingState.FAILURE_INIT }
                    }
                }
            } catch (exception: Exception) {
                if (exception !is CancellationException) {
                    _pagingState.update { PagingState.FAILURE_INIT }
                }
            }
        }
    }

    suspend fun load() {
        if (pagingState.value != PagingState.IDLE) return

        pagingDataRequestJob?.cancel()
        _pagingState.update { PagingState.LOADING_NEXT }

        pagingDataRequestJob = coroutineScope.launch {
            try {
                val response = pagingSource.load(pageIndex = currentPageIndex, pageSize = perPage)
                when (response) {
                    is PagingLoadResult.Success -> {
                        val itemList = response.result
                        applyResponse(itemList)
                    }
                    is PagingLoadResult.Error -> {
                        _pagingState.update { PagingState.FAILURE_NEXT }
                    }
                }
            } catch (exception: Exception) {
                if (exception !is CancellationException) {
                    _pagingState.update { PagingState.FAILURE_NEXT }
                }
            }
        }
    }

    private fun applyResponse(dataInResponse: List<ITEM>, multiple: Int = 1) {
        if (dataInResponse.size < perPage * multiple) {
            _pagingState.update { PagingState.LAST }
        } else {
            _pagingState.update { PagingState.IDLE }
        }
        _pagingData.update { _pagingData.value + dataInResponse }
        currentPageIndex += multiple
    }

    fun clear() {
        pagingDataRequestJob?.cancel()
        _pagingData.update { emptyList() }
        _pagingState.update { PagingState.IDLE }
    }

    fun deleteItem(targetItemKey: KEY) {
        val currentDataList = _pagingData.value
        _pagingData.update { currentDataList.filter { getKeyFromItem(it) != targetItemKey } }
    }

    fun modifyItem(targetItem: ITEM) {
        val currentDataList = _pagingData.value

        _pagingData.update {
            currentDataList.map { item ->
                if (getKeyFromItem(targetItem) == getKeyFromItem(item)) {
                    targetItem
                } else {
                    item
                }
            }
        }
    }
}
