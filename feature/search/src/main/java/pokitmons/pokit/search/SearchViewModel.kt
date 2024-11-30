package pokitmons.pokit.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pokitmons.pokit.core.feature.model.paging.PagingLoadResult
import pokitmons.pokit.core.feature.model.paging.PagingSource
import pokitmons.pokit.core.feature.model.paging.PagingState
import pokitmons.pokit.core.feature.model.paging.SimplePaging
import pokitmons.pokit.core.feature.navigation.args.LinkUpdateEvent
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.link.LinksSort
import pokitmons.pokit.domain.usecase.link.DeleteLinkUseCase
import pokitmons.pokit.domain.usecase.link.GetLinkUseCase
import pokitmons.pokit.domain.usecase.link.SearchLinksUseCase
import pokitmons.pokit.domain.usecase.link.SetBookmarkUseCase
import pokitmons.pokit.domain.usecase.pokit.GetPokitsUseCase
import pokitmons.pokit.domain.usecase.search.AddRecentSearchWordUseCase
import pokitmons.pokit.domain.usecase.search.GetRecentSearchWordsUseCase
import pokitmons.pokit.domain.usecase.search.GetUseRecentSearchWordsUseCase
import pokitmons.pokit.domain.usecase.search.RemoveRecentSearchWordUseCase
import pokitmons.pokit.domain.usecase.search.SetUseRecentSearchWordsUseCase
import pokitmons.pokit.search.model.Filter
import pokitmons.pokit.search.model.FilterType
import pokitmons.pokit.search.model.Link
import pokitmons.pokit.search.model.LinkBottomSheetState
import pokitmons.pokit.search.model.Pokit
import pokitmons.pokit.search.model.SearchScreenState
import pokitmons.pokit.search.model.SearchScreenStep
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    searchLinksUseCase: SearchLinksUseCase,
    getPokitsUseCase: GetPokitsUseCase,
    getRecentSearchWordsUseCase: GetRecentSearchWordsUseCase,
    getUseRecentSearchWordsUseCase: GetUseRecentSearchWordsUseCase,
    private val getLinkUseCase: GetLinkUseCase,
    private val deleteLinkUseCase: DeleteLinkUseCase,
    private val setUseRecentSearchWordsUseCase: SetUseRecentSearchWordsUseCase,
    private val addRecentSearchWordUseCase: AddRecentSearchWordUseCase,
    private val removeRecentSearchWordUseCase: RemoveRecentSearchWordUseCase,
    private val setBookmarkUseCase: SetBookmarkUseCase,
) : ViewModel() {

    init {
        initLinkUpdateEventDetector()
        initLinkRemoveEventDetector()
    }

    private val linkPagingSource = object : PagingSource<Link> {
        override suspend fun load(pageIndex: Int, pageSize: Int): PagingLoadResult<Link> {
            val currentFilter = state.value.filter ?: Filter()

            val isRead = if (currentFilter.notRead) false else null
            val favorites = if (currentFilter.bookmark) true else null
            val sort = if (state.value.sortRecent) LinksSort.RECENT else LinksSort.OLDER
            val currentAppliedSearchWord = appliedSearchWord

            val response = searchLinksUseCase.searchLinks(
                page = pageIndex,
                size = pageSize,
                sort = listOf(sort.value),
                isRead = isRead,
                favorites = favorites,
                startDate = currentFilter.startDate?.toDateString(),
                endDate = currentFilter.endDate?.toDateString(),
                categoryIds = currentFilter.selectedPokits.mapNotNull { it.id.toIntOrNull() },
                searchWord = currentAppliedSearchWord
            )
            return PagingLoadResult.fromPokitResult(
                pokitResult = response,
                mapper = { domainLinks -> domainLinks.map { Link.fromDomainLink(it) } }
            )
        }
    }

    private val linkPaging = SimplePaging(
        pagingSource = linkPagingSource,
        getKeyFromItem = { link -> link.id },
        coroutineScope = viewModelScope
    )

    private val pokitPagingSource = object : PagingSource<Pokit> {
        override suspend fun load(pageIndex: Int, pageSize: Int): PagingLoadResult<Pokit> {
            val response = getPokitsUseCase.getPokits(page = pageIndex, size = pageSize)
            return PagingLoadResult.fromPokitResult(
                pokitResult = response,
                mapper = { domainPokits -> domainPokits.map { Pokit.fromDomainPokit(it) } }
            )
        }
    }

    private val pokitPaging = SimplePaging(
        pagingSource = pokitPagingSource,
        getKeyFromItem = { pokit -> pokit.id },
        coroutineScope = viewModelScope
    )

    val linkList: StateFlow<List<Link>> = linkPaging.pagingData
    val linkPagingState: StateFlow<PagingState> = linkPaging.pagingState

    val pokitList: StateFlow<List<Pokit>> = pokitPaging.pagingData
    val pokitPagingState: StateFlow<PagingState> = pokitPaging.pagingState

    private val _searchWord = MutableStateFlow("")
    val searchWord = _searchWord.asStateFlow()

    private val _state = MutableStateFlow(SearchScreenState())
    val state = combine(
        _state,
        getRecentSearchWordsUseCase.getWords(),
        getUseRecentSearchWordsUseCase.getUse()
    ) { state, searchWords, useRecentSearchWord ->
        state.copy(recentSearchWords = searchWords, useRecentSearchWord = useRecentSearchWord)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = SearchScreenState()
    )

    private var appliedSearchWord = ""

    private fun initLinkUpdateEventDetector() {
        viewModelScope.launch {
            LinkUpdateEvent.updatedLink.collectLatest { updatedLink ->
                val targetLink = linkPaging.pagingData.value.find { it.id == updatedLink.id.toString() } ?: return@collectLatest
                val modifiedLink = targetLink.copy(title = updatedLink.title, imageUrl = updatedLink.thumbnail, domainUrl = updatedLink.domain)
                linkPaging.modifyItem(modifiedLink)
            }
        }
    }

    private fun initLinkRemoveEventDetector() {
        viewModelScope.launch {
            LinkUpdateEvent.removedLink.collectLatest { removedLinkId ->
                val targetItem = linkPaging.pagingData.value.find { it.id == removedLinkId.toString() } ?: return@collectLatest
                linkPaging.deleteItem(targetItem.id)
            }
        }
    }

    fun inputSearchWord(newSearchWord: String) {
        _searchWord.update { newSearchWord }
        val currentState = state.value

        if (newSearchWord.isEmpty() && currentState.step == SearchScreenStep.RESULT) {
            _state.update { state ->
                state.copy(step = SearchScreenStep.INPUT)
            }
        }
    }

    fun applyCurrentSearchWord() {
        appliedSearchWord = searchWord.value

        if (appliedSearchWord.isNotEmpty()) {
            _state.update { state ->
                state.copy(step = SearchScreenStep.RESULT)
            }
            viewModelScope.launch {
                addRecentSearchWordUseCase.addRecentSearchWord(appliedSearchWord)
                linkPaging.refresh()
            }
        }
    }

    fun applySearchWord(word: String) {
        appliedSearchWord = word
        _searchWord.update { word }

        if (appliedSearchWord.isNotEmpty()) {
            _state.update { state ->
                state.copy(step = SearchScreenStep.RESULT)
            }
            viewModelScope.launch {
                addRecentSearchWordUseCase.addRecentSearchWord(appliedSearchWord)
                linkPaging.refresh()
            }
        }
    }

    fun toggleUseRecentSearchWord() {
        val currentUseRecentSearchWord = state.value.useRecentSearchWord
        viewModelScope.launch {
            setUseRecentSearchWordsUseCase.setUse(!currentUseRecentSearchWord)
        }
    }

    fun removeRecentSearchWord(word: String) {
        viewModelScope.launch {
            removeRecentSearchWordUseCase.removeWord(word)
        }
    }

    fun removeAllRecentSearchWord() {
        viewModelScope.launch {
            removeRecentSearchWordUseCase.removeAll()
        }
    }

    fun showFilterBottomSheet() {
        _state.update { state ->
            state.copy(
                showFilterBottomSheet = true
            )
        }
    }

    fun showFilterBottomSheetWithType(type: FilterType) {
        _state.update { state ->
            state.copy(
                showFilterBottomSheet = true,
                firstBottomSheetFilterType = type
            )
        }
    }

    fun hideFilterBottomSheet() {
        _state.update { state ->
            state.copy(
                showFilterBottomSheet = false
            )
        }
    }

    fun showLinkRemoveBottomSheet(link: Link) {
        _state.update { state ->
            state.copy(
                linkBottomSheetType = LinkBottomSheetState.CheckRemove(link = link)
            )
        }
    }

    fun showLinkDetailBottomSheet(link: Link) {
        _state.update { state ->
            state.copy(
                linkBottomSheetType = LinkBottomSheetState.LinkDetail(link = link)
            )
        }

        viewModelScope.launch {
            val response = getLinkUseCase.getLink(link.id.toInt())
            val currentBottomSheetState = state.value.linkBottomSheetType ?: return@launch

            val currentShowDetailLinkBottomSheet = (currentBottomSheetState is LinkBottomSheetState.LinkDetail) &&
                (currentBottomSheetState.link.id == link.id)

            if (response is PokitResult.Success && currentShowDetailLinkBottomSheet) {
                val responseLink = Link.fromDomainLink(response.result).copy(imageUrl = link.imageUrl, isRead = true)
                _state.update {
                    it.copy(linkBottomSheetType = LinkBottomSheetState.LinkDetail(link = responseLink))
                }
            }

            val isReadChangedLink = linkPaging.pagingData.value
                .find { it.id == link.id }
                ?.copy(isRead = true) ?: return@launch

            linkPaging.modifyItem(isReadChangedLink)
        }
    }

    fun hideLinkBottomSheet() {
        _state.update { state ->
            state.copy(
                linkBottomSheetType = null
            )
        }
    }

    fun setFilter(filter: Filter) {
        _state.update { state ->
            state.copy(
                showFilterBottomSheet = false,
                filter = if (filter == Filter.DefaultFilter) {
                    null
                } else {
                    filter
                }
            )
        }

        viewModelScope.launch {
            linkPaging.refresh()
        }
    }

    fun toggleSortOrder() {
        _state.update { state ->
            state.copy(sortRecent = !state.sortRecent)
        }

        viewModelScope.launch {
            linkPaging.refresh()
        }
    }

    fun loadNextLinks() {
        viewModelScope.launch {
            linkPaging.load()
        }
    }

    fun loadNextPokits() {
        viewModelScope.launch {
            pokitPaging.load()
        }
    }

    fun refreshPokits() {
        viewModelScope.launch {
            pokitPaging.refresh()
        }
    }

    fun toggleBookmarkInBottomSheet() {
        val currentState = state.value
        val bottomSheetLink = currentState.linkBottomSheetType?.link ?: return
        val bottomSheetLinkId = bottomSheetLink.id.toIntOrNull() ?: return
        val applyBookmarked = !bottomSheetLink.bookmark

        viewModelScope.launch {
            val response = setBookmarkUseCase.setBookMarked(bottomSheetLinkId, applyBookmarked)
            if (response is PokitResult.Success) {
                val bookmarkChangedLink = bottomSheetLink.copy(bookmark = applyBookmarked)

                if (currentState.linkBottomSheetType is LinkBottomSheetState.LinkDetail) {
                    _state.update { state ->
                        state.copy(
                            linkBottomSheetType = LinkBottomSheetState.LinkDetail(link = bookmarkChangedLink)
                        )
                    }
                }

                linkPaging.modifyItem(bookmarkChangedLink)
            }
        }
    }

    fun deleteLink() {
        val currentLinkId = state.value.linkBottomSheetType?.link?.id?.toIntOrNull() ?: return
        viewModelScope.launch {
            val response = deleteLinkUseCase.deleteLink(currentLinkId)
            if (response is PokitResult.Success) {
                LinkUpdateEvent.removeSuccess(currentLinkId)
                val targetLink = linkPaging.pagingData.value.find { it.id == currentLinkId.toString() } ?: return@launch
                linkPaging.deleteItem(targetLink.id)
            }
        }
    }
}
