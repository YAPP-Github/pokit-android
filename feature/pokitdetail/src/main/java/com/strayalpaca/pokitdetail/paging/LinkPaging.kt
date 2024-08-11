package com.strayalpaca.pokitdetail.paging

import com.strayalpaca.pokitdetail.model.Link
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.link.LinksSort
import kotlin.reflect.KSuspendFunction4
import pokitmons.pokit.domain.model.link.Link as DomainLink

class LinkPaging(
    private var getLinks: KSuspendFunction4<Int, Int, Int, LinksSort, PokitResult<List<DomainLink>>>,
    private val perPage: Int = 10,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO),
    private val initPage: Int = 0,
    private val firstRequestPage: Int = 3,
    initCategoryId: Int = 0,
) : SimplePaging<Link> {
    private val _pagingState = MutableStateFlow(SimplePagingState.IDLE)
    override val pagingState: StateFlow<SimplePagingState> = _pagingState.asStateFlow()

    private val _pagingData: MutableStateFlow<List<Link>> = MutableStateFlow(emptyList())
    override val pagingData: StateFlow<List<Link>> = _pagingData.asStateFlow()
    private var currentPageIndex = initPage
    private var requestJob: Job? = null

    private var currentCategoryId: Int = initCategoryId
    private var currentSort = LinksSort.RECENT

    fun changeOptions(categoryId: Int, sort: LinksSort) {
        currentCategoryId = categoryId
        currentSort = sort
    }

    override suspend fun refresh() {
        requestJob?.cancel()

        _pagingData.update { emptyList() }
        _pagingState.update { SimplePagingState.LOADING_INIT }
        requestJob = coroutineScope.launch {
            try {
                currentPageIndex = initPage
                val response = getLinks(currentCategoryId, perPage * firstRequestPage, currentPageIndex, currentSort)
                when (response) {
                    is PokitResult.Success -> {
                        val links = response.result.map { domainLink ->
                            Link.fromDomainLink(domainLink)
                        }
                        applyResponse(links, firstRequestPage)
                    }

                    is PokitResult.Error -> {
                        _pagingState.update { SimplePagingState.FAILURE_INIT }
                    }
                }
            } catch (exception: Exception) {
                if (exception !is CancellationException) {
                    _pagingState.update { SimplePagingState.FAILURE_INIT }
                }
            }
        }
    }

    override suspend fun load() {
        if (pagingState.value != SimplePagingState.IDLE) return

        requestJob?.cancel()
        _pagingState.update { SimplePagingState.LOADING_NEXT }

        requestJob = coroutineScope.launch {
            try {
                val response = getLinks(currentCategoryId, perPage, currentPageIndex, currentSort)
                when (response) {
                    is PokitResult.Success -> {
                        val links = response.result.map { domainLink ->
                            Link.fromDomainLink(domainLink)
                        }
                        applyResponse(links)
                    }

                    is PokitResult.Error -> {
                        _pagingState.update { SimplePagingState.FAILURE_NEXT }
                    }
                }
            } catch (exception: Exception) {
                if (exception !is CancellationException) {
                    _pagingState.update { SimplePagingState.FAILURE_NEXT }
                }
            }
        }
    }

    private fun applyResponse(dataInResponse: List<Link>, multiple: Int = 1) {
        if (dataInResponse.size < perPage * multiple) {
            _pagingState.update { SimplePagingState.LAST }
        } else {
            _pagingState.update { SimplePagingState.IDLE }
        }
        _pagingData.update { _pagingData.value + dataInResponse }
        currentPageIndex += multiple
    }

    override fun clear() {
        requestJob?.cancel()
        _pagingData.update { emptyList() }
        _pagingState.update { SimplePagingState.IDLE }
    }

    override suspend fun deleteItem(item: Link) {
        val capturedDataList = _pagingData.value
        _pagingData.update { capturedDataList.filter { it.id != item.id } }
    }

    override suspend fun modifyItem(item: Link) {
        val capturedDataList = _pagingData.value
        val targetPokit = capturedDataList.find { it.id == item.id } ?: return

        _pagingData.update {
            capturedDataList.map { pokit ->
                if (targetPokit.id == pokit.id) {
                    targetPokit
                } else {
                    pokit
                }
            }
        }
    }
}
