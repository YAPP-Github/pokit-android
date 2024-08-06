package com.strayalpaca.pokitdetail.paging

import com.strayalpaca.pokitdetail.model.Pokit
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
import kotlin.reflect.KSuspendFunction2
import pokitmons.pokit.domain.model.pokit.Pokit as DomainPokit

class PokitPaging(
    private val getPokits: KSuspendFunction2<Int, Int, PokitResult<List<DomainPokit>>>,
    private val perPage: Int = 10,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO),
    private val initPage: Int = 0,
    private val firstRequestPage: Int = 3
) : SimplePaging<Pokit> {
    private val _pagingState = MutableStateFlow(SimplePagingState.IDLE)
    override val pagingState: StateFlow<SimplePagingState> = _pagingState.asStateFlow()

    private val _pagingData: MutableStateFlow<List<Pokit>> = MutableStateFlow(emptyList())
    override val pagingData: StateFlow<List<Pokit>> = _pagingData.asStateFlow()
    private var currentPageIndex = initPage
    private var requestJob: Job? = null

    override suspend fun refresh() {
        requestJob?.cancel()

        _pagingState.update { SimplePagingState.LOADING_INIT }
        requestJob = coroutineScope.launch {
            try {
                currentPageIndex = initPage
                val response = getPokits(perPage * firstRequestPage, currentPageIndex)
                when (response) {
                    is PokitResult.Success -> {
                        val pokitList = response.result.map { domainPokit ->
                            Pokit.fromDomainPokit(domainPokit)
                        }
                        applyResponse(pokitList, firstRequestPage)
                    }

                    is PokitResult.Error -> {
                        _pagingState.update { SimplePagingState.FAILURE_INIT }
                    }
                }
            } catch (exception: Exception) {
                if (exception !is CancellationException)
                    _pagingState.update { SimplePagingState.FAILURE_INIT }

            }
        }
    }


    override suspend fun load() {
        if (pagingState.value != SimplePagingState.IDLE) return

        requestJob?.cancel()
        _pagingState.update { SimplePagingState.LOADING_NEXT }

        requestJob = coroutineScope.launch {
            try {
                val response = getPokits(perPage, currentPageIndex)
                when (response) {
                    is PokitResult.Success -> {
                        val pokitList = response.result.map { domainPokit ->
                            Pokit.fromDomainPokit(domainPokit)
                        }
                        applyResponse(pokitList)
                    }

                    is PokitResult.Error -> {
                        _pagingState.update { SimplePagingState.FAILURE_NEXT }
                    }
                }
            } catch (exception: Exception) {
                if (exception !is CancellationException)
                    _pagingState.update { SimplePagingState.FAILURE_NEXT }

            }
        }
    }

    private fun applyResponse(dataInResponse: List<Pokit>, multiple: Int = 1) {
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

    override suspend fun deleteItem(item: Pokit) {
        val capturedDataList = _pagingData.value
        _pagingData.update { capturedDataList.filter { it.id != item.id } }
    }

    override suspend fun modifyItem(item: Pokit) {
        val capturedDataList = _pagingData.value
        val targetPokit = capturedDataList.find { it.id == item.id } ?: return

        _pagingData.update {
            capturedDataList.map { pokit ->
                if (targetPokit.id == pokit.id) targetPokit
                else pokit
            }
        }
    }
}
