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
import pokitmons.pokit.core.feature.navigation.args.LinkUpdateEvent
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.usecase.link.DeleteLinkUseCase
import pokitmons.pokit.domain.usecase.link.SearchLinksUseCase
import pokitmons.pokit.domain.usecase.link.SetBookmarkUseCase
import pokitmons.pokit.domain.usecase.pokit.GetPokitsUseCase
import pokitmons.pokit.domain.usecase.search.AddRecentSearchWordUseCase
import pokitmons.pokit.domain.usecase.search.GetRecentSearchWordsUseCase
import pokitmons.pokit.domain.usecase.search.GetUseRecentSearchWordsUseCase
import pokitmons.pokit.domain.usecase.search.RemoveRecentSearchWordUseCase
import pokitmons.pokit.domain.usecase.search.SetUseRecentSearchWordsUseCase
import pokitmons.pokit.search.model.BottomSheetType
import pokitmons.pokit.search.model.Filter
import pokitmons.pokit.search.model.FilterType
import pokitmons.pokit.search.model.Link
import pokitmons.pokit.search.model.Pokit
import pokitmons.pokit.search.model.SearchScreenState
import pokitmons.pokit.search.model.SearchScreenStep
import pokitmons.pokit.search.paging.LinkPaging
import pokitmons.pokit.search.paging.PokitPaging
import pokitmons.pokit.search.paging.SimplePagingState
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    searchLinksUseCase: SearchLinksUseCase,
    getPokitsUseCase: GetPokitsUseCase,
    getRecentSearchWordsUseCase: GetRecentSearchWordsUseCase,
    getUseRecentSearchWordsUseCase: GetUseRecentSearchWordsUseCase,
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

    private val linkPaging = LinkPaging(
        searchLinksUseCase = searchLinksUseCase,
        filter = Filter(),
        perPage = 10,
        coroutineScope = viewModelScope,
        initPage = 0
    )

    private val pokitPaging = PokitPaging(
        getPokits = getPokitsUseCase,
        perPage = 10,
        coroutineScope = viewModelScope,
        initPage = 0
    )

    val linkList: StateFlow<List<Link>> = linkPaging.pagingData
    val linkPagingState: StateFlow<SimplePagingState> = linkPaging.pagingState

    val pokitList: StateFlow<List<Pokit>> = pokitPaging.pagingData
    val pokitPagingState: StateFlow<SimplePagingState> = pokitPaging.pagingState

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
                linkPaging.deleteItem(targetItem)
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
                linkPaging.changeSearchWord(appliedSearchWord)
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
                linkPaging.changeSearchWord(appliedSearchWord)
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

    fun showLinkModifyBottomSheet(link: Link) {
        _state.update { state ->
            state.copy(
                linkBottomSheetType = BottomSheetType.MODIFY,
                currentTargetLink = link
            )
        }
    }

    fun showLinkRemoveBottomSheet(link: Link) {
        _state.update { state ->
            state.copy(
                linkBottomSheetType = BottomSheetType.REMOVE,
                showLinkDetailBottomSheet = false,
                currentTargetLink = link
            )
        }
    }

    fun hideLinkModifyBottomSheet() {
        _state.update { state ->
            state.copy(
                linkBottomSheetType = null,
                currentTargetLink = null
            )
        }
    }

    fun showLinkDetailBottomSheet(link: Link) {
        _state.update { state ->
            state.copy(
                currentDetailLink = link,
                showLinkDetailBottomSheet = true,
                linkBottomSheetType = null
            )
        }
    }

    fun hideLinkDetailBottomSheet() {
        _state.update { state ->
            state.copy(
                currentDetailLink = null,
                showLinkDetailBottomSheet = false
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
            linkPaging.changeFilter(filter)
            linkPaging.refresh()
        }
    }

    fun toggleSortOrder() {
        _state.update { state ->
            state.copy(sortRecent = !state.sortRecent)
        }

        viewModelScope.launch {
            linkPaging.changeRecentSort(state.value.sortRecent)
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

    fun toggleBookmark() {
        val currentLink = state.value.currentTargetLink ?: return
        val currentLinkId = currentLink.id.toIntOrNull() ?: return
        val applyBookmarked = !currentLink.bookmark

        viewModelScope.launch {
            val response = setBookmarkUseCase.setBookMarked(currentLinkId, applyBookmarked)
            if (response is PokitResult.Success) {
                val bookmarkChangedLink = currentLink.copy(bookmark = applyBookmarked)
                _state.update { state ->
                    state.copy(
                        currentDetailLink = bookmarkChangedLink
                    )
                }
                linkPaging.modifyItem(bookmarkChangedLink)
            }
        }
    }

    fun deleteLink() {
        val currentLinkId = state.value.currentTargetLink?.id?.toIntOrNull() ?: return
        viewModelScope.launch {
            val response = deleteLinkUseCase.deleteLink(currentLinkId)
            if (response is PokitResult.Success) {
                LinkUpdateEvent.removeSuccess(currentLinkId)
                val targetLink = linkPaging.pagingData.value.find { it.id == currentLinkId.toString() } ?: return@launch
                linkPaging.deleteItem(targetLink)
            }
        }
    }
}
