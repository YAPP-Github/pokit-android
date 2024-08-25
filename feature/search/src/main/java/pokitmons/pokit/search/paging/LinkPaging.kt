package pokitmons.pokit.search.paging

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.usecase.link.SearchLinksUseCase
import pokitmons.pokit.search.model.Filter
import pokitmons.pokit.search.model.Link
import kotlin.coroutines.cancellation.CancellationException

class LinkPaging(
    private val searchLinksUseCase: SearchLinksUseCase,
    private val perPage: Int = 10,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO),
    private val initPage: Int = 0,
    private val firstRequestPage: Int = 3,
    private var filter: Filter,
    private var searchWord: String = "",
    private var recentSort: Boolean = true,
) : SimplePaging<Link> {

    private val _pagingState: MutableStateFlow<SimplePagingState> = MutableStateFlow(SimplePagingState.IDLE)
    override val pagingState: StateFlow<SimplePagingState> = _pagingState.asStateFlow()

    private val _pagingData: MutableStateFlow<List<Link>> = MutableStateFlow(emptyList())
    override val pagingData: StateFlow<List<Link>> = _pagingData.asStateFlow()

    private var currentPageIndex = initPage
    private var requestJob: Job? = null

    fun changeFilter(filter: Filter) {
        this.filter = filter
    }

    fun changeSearchWord(searchWord: String) {
        this.searchWord = searchWord
    }

    fun changeRecentSort(recentSort: Boolean) {
        this.recentSort = recentSort
    }

    override suspend fun refresh() {
        requestJob?.cancel()

        _pagingData.update { emptyList() }
        _pagingState.update { SimplePagingState.LOADING_INIT }
        requestJob = coroutineScope.launch {
            try {
                currentPageIndex = initPage
                val response = searchLinksUseCase.searchLinks(
                    page = currentPageIndex,
                    size = perPage * firstRequestPage,
                    sort = listOf(),
                    isRead = if (!filter.notRead) false else null,
                    favorites = if (filter.bookmark) true else null,
                    startDate = filter.startDate?.toDateString(),
                    endDate = filter.endDate?.toDateString(),
                    categoryIds = filter.selectedPokits.mapNotNull { it.id.toIntOrNull() },
                    searchWord = searchWord
                )
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
                val response = searchLinksUseCase.searchLinks(
                    page = currentPageIndex,
                    size = perPage,
                    sort = listOf(),
                    isRead = if (!filter.notRead) false else null,
                    favorites = if (filter.bookmark) true else null,
                    startDate = filter.startDate?.toDateString(),
                    endDate = filter.endDate?.toDateString(),
                    categoryIds = filter.selectedPokits.mapNotNull { it.id.toIntOrNull() },
                    searchWord = searchWord
                )
                when (response) {
                    is PokitResult.Success -> {
                        val links = response.result.map { domainLink ->
                            Link.fromDomainLink(domainLink)
                        }
                        applyResponse(links)
                    }
                    is PokitResult.Error -> {
                        _pagingState.update { SimplePagingState.FAILURE_INIT }
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
                    item
                } else {
                    pokit
                }
            }
        }
    }
}
