package pokitmons.pokit.search

import kotlinx.coroutines.flow.StateFlow
import pokitmons.pokit.core.feature.model.paging.PagingState
import pokitmons.pokit.search.model.Filter
import pokitmons.pokit.search.model.FilterType
import pokitmons.pokit.search.model.Link
import pokitmons.pokit.search.model.Pokit
import pokitmons.pokit.search.model.SearchScreenState

interface SearchViewModel {
    val linkList: StateFlow<List<Link>>
    val linkPagingState: StateFlow<PagingState>
    val pokitList: StateFlow<List<Pokit>>
    val pokitPagingState: StateFlow<PagingState>
    val state: StateFlow<SearchScreenState>

    fun inputSearchWord(searchWord: String)
    fun searchByCurrentSearchWord()
    fun inputSearchWordThenSearch(word: String)

    fun toggleUseRecentSearchWord()
    fun removeRecentSearchWord(word: String)
    fun removeAllRecentSearchWord()

    fun showFilterBottomSheet()
    fun showFilterBottomSheetWithType(type: FilterType)
    fun hideFilterBottomSheet()

    fun showLinkRemoveBottomSheet(link: Link)
    fun showLinkDetailBottomSheet(link: Link)
    fun hideLinkBottomSheet()

    fun setFilter(filter: Filter)
    fun toggleSortOrder()

    fun loadNextLinks()
    fun loadNextPokits()
    fun refreshPokits()

    fun toggleBookmark(link: Link)
    fun deleteLink(link: Link)
}
