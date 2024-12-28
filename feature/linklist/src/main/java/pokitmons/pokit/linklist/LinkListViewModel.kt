package pokitmons.pokit.linklist

import kotlinx.coroutines.flow.StateFlow
import pokitmons.pokit.core.feature.model.paging.PagingState
import pokitmons.pokit.linklist.model.Link
import pokitmons.pokit.linklist.model.LinkListScreenState

interface LinkListViewModel {
    val state: StateFlow<LinkListScreenState>
    val linkList: StateFlow<List<Link>>
    val linkListState: StateFlow<PagingState>

    fun loadNextLinks()
    fun refreshLinks()
    fun toggleSortType()
    fun toggleBookmark(link: Link)
    fun showLinkDetailBottomSheet(link: Link)
    fun hideLinkDetailBottomSheet()
    fun showCheckLinkRemoveBottomSheet()
    fun hideCheckLinkRemoveBottomSheet()
    fun removeLink(linkId: String)
}
