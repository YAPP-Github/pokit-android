package pokitmons.pokit.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pokitmons.pokit.domain.usecase.link.SearchLinksUseCase
import pokitmons.pokit.domain.usecase.pokit.GetPokitsUseCase
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
) : ViewModel() {
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

    val linkList : StateFlow<List<Link>> = linkPaging.pagingData
    val linkPagingState : StateFlow<SimplePagingState> = linkPaging.pagingState

    val pokitList : StateFlow<List<Pokit>> = pokitPaging.pagingData
    val pokitPagingState : StateFlow<SimplePagingState> = pokitPaging.pagingState

    private val _searchWord = MutableStateFlow("")
    val searchWord = _searchWord.asStateFlow()

    private val _state = MutableStateFlow(SearchScreenState())
    val state = _state.asStateFlow()

    private var appliedSearchWord = ""

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
                linkPaging.changeSearchWord(appliedSearchWord)
                linkPaging.refresh()
            }
        }
    }

    fun toggleUseRecentSearchWord() {
        _state.update { state ->
            state.copy(useRecentSearchWord = !state.useRecentSearchWord)
        }
    }

    fun removeRecentSearchWord(word: String) {
        _state.update { state ->
            state.copy(recentSearchWords = state.recentSearchWords.filter { name -> name != word })
        }
    }

    fun removeAllRecentSearchWord() {
        _state.update { state ->
            state.copy(
                recentSearchWords = emptyList()
            )
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
                currentLink = link
            )
        }
    }

    fun hideLinkModifyBottomSheet() {
        _state.update { state ->
            state.copy(
                currentLink = null
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
}
