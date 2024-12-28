package pokitmons.pokit.linklist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pokitmons.pokit.core.feature.model.paging.PagingLoadResult
import pokitmons.pokit.core.feature.model.paging.PagingSource
import pokitmons.pokit.core.feature.model.paging.PagingState
import pokitmons.pokit.core.feature.model.paging.SimplePaging
import pokitmons.pokit.core.feature.navigation.args.LinkUpdateEvent
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.home.remind.RemindResult
import pokitmons.pokit.domain.usecase.home.remind.BookMarkContentsCountUseCase
import pokitmons.pokit.domain.usecase.home.remind.BookMarkContentsUseCase
import pokitmons.pokit.domain.usecase.home.remind.UnReadContentsCountUseCase
import pokitmons.pokit.domain.usecase.home.remind.UnReadContentsUseCase
import pokitmons.pokit.domain.usecase.link.DeleteLinkUseCase
import pokitmons.pokit.domain.usecase.link.GetLinkUseCase
import pokitmons.pokit.domain.usecase.link.SetBookmarkUseCase
import pokitmons.pokit.linklist.model.BottomSheetInfo
import pokitmons.pokit.linklist.model.BottomSheetType
import pokitmons.pokit.linklist.model.Link
import pokitmons.pokit.linklist.model.LinkListScreenState
import pokitmons.pokit.linklist.model.LinkListScreenType
import pokitmons.pokit.linklist.model.LinkSort
import javax.inject.Inject

@HiltViewModel
class LinkListViewModelImpl @Inject constructor(
    private val bookMarkContentsUseCase: BookMarkContentsUseCase,
    private val bookMarkContentsCountUseCase: BookMarkContentsCountUseCase,
    private val unReadContentsUseCase: UnReadContentsUseCase,
    private val unReadContentsCountUseCase: UnReadContentsCountUseCase,
    private val getLinkUseCase: GetLinkUseCase,
    private val deleteLinkUseCase: DeleteLinkUseCase,
    private val setBookmarkUseCase: SetBookmarkUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel(), LinkListViewModel {
    private val defaultDispatcher = Dispatchers.IO

    private val linkPagingSource = object : PagingSource<Link> {
        override suspend fun load(pageIndex: Int, pageSize: Int): PagingLoadResult<Link> {
            val sort = LinkSort.toPokitsSort(state.value.sort)

            val response: PokitResult<List<RemindResult>> = if (state.value.type == LinkListScreenType.Bookmark) {
                bookMarkContentsUseCase.getBookmarkContents(sort = sort)
            } else {
                unReadContentsUseCase.getUnreadContents(sort = sort)
            }
            return PagingLoadResult.fromPokitResult(
                pokitResult = response,
                mapper = { remindResults -> remindResults.map { Link.fromRemindResult(it) } }
            )
        }
    }

    private val linkPaging = SimplePaging(
        pagingSource = linkPagingSource,
        getKeyFromItem = { remindResult -> remindResult.id },
        coroutineScope = viewModelScope
    )

    private val type: LinkListScreenType = savedStateHandle.get<String>("type")?.let { LinkListScreenType.getByKey(it) } ?: LinkListScreenType.Unread
    private val _state = MutableStateFlow(LinkListScreenState(sort = LinkSort.RECENT, type = type, count = 0))
    override val state: StateFlow<LinkListScreenState>
        get() = _state.asStateFlow()

    override val linkList: StateFlow<List<Link>>
        get() = linkPaging.pagingData
    override val linkListState: StateFlow<PagingState>
        get() = linkPaging.pagingState

    init {
        initLinkRemoveEventDetector()
        refreshLinks()
        updateContentsCount()
    }

    private fun initLinkRemoveEventDetector() {
        viewModelScope.launch {
            LinkUpdateEvent.removedLink.collectLatest { removedLinkId ->
                val targetLink = linkPaging.pagingData.value.find { it.id == removedLinkId.toString() } ?: return@collectLatest
                linkPaging.deleteItem(targetLink.id)
            }
        }
    }

    override fun loadNextLinks() {
        viewModelScope.launch(defaultDispatcher) {
            linkPaging.load()
        }
    }

    override fun refreshLinks() {
        viewModelScope.launch(defaultDispatcher) {
            linkPaging.refresh()
        }
    }

    override fun toggleSortType() {
        val sort = LinkSort.toggle(state.value.sort)
        viewModelScope.launch(defaultDispatcher) {
            _state.update { it.copy(sort = sort, count = 0) }
            updateContentsCount()
            linkPaging.refresh()
        }
    }

    private fun updateContentsCount() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = if (state.value.type == LinkListScreenType.Bookmark) {
                bookMarkContentsCountUseCase.getCount()
            } else {
                unReadContentsCountUseCase.getCount()
            }

            if (response is PokitResult.Success) {
                _state.update { it.copy(count = response.result) }
            }
        }
    }

    override fun toggleBookmark(link: Link) {
        val newBookmarkState = !link.bookmark
        val linkId = link.id.toIntOrNull() ?: return

        viewModelScope.launch(defaultDispatcher) {
            val response = setBookmarkUseCase.setBookMarked(linkId, newBookmarkState)
            if (response is PokitResult.Success) {
                val bookmarkChangedLink = linkPaging.pagingData.value
                    .find { it.id == link.id }
                    ?.copy(bookmark = newBookmarkState) ?: return@launch
                linkPaging.modifyItem(bookmarkChangedLink)

                // 마음에 안듦
                if (link.id == state.value.bottomSheetInfo?.link?.id) {
                    _state.update { state ->
                        state.copy(
                            bottomSheetInfo = state.bottomSheetInfo?.copy(link = bookmarkChangedLink)
                        )
                    }
                }
            }
        }
    }

    override fun showLinkDetailBottomSheet(link: Link) {
        _state.update {
            it.copy(bottomSheetInfo = BottomSheetInfo(type = BottomSheetType.DETAIL, link = link))
        }

        viewModelScope.launch {
            val response = getLinkUseCase.getLink(link.id.toInt())
            if (response is PokitResult.Success &&
                state.value.bottomSheetInfo?.link?.id == link.id &&
                state.value.bottomSheetInfo?.type == BottomSheetType.DETAIL
            ) {
                val responseLink = Link.fromDomainLink(response.result).copy(imageUrl = link.imageUrl, isRead = true)
                _state.update { state ->
                    state.copy(
                        bottomSheetInfo = state.bottomSheetInfo?.copy(link = responseLink)
                    )
                }
            }

            val isReadChangedLink = linkPaging.pagingData.value
                .find { it.id == link.id }
                ?.copy(isRead = true) ?: return@launch

            linkPaging.modifyItem(isReadChangedLink)
        }
    }

    override fun hideLinkDetailBottomSheet() {
        if (_state.value.bottomSheetInfo?.type != BottomSheetType.DETAIL) return
        _state.update {
            it.copy(bottomSheetInfo = null)
        }
    }

    override fun showCheckLinkRemoveBottomSheet() {
        val currentState = _state.value.copy()

        val isInvokedFromDetailBottomSheet = (currentState.bottomSheetInfo?.type == BottomSheetType.DETAIL)
        if (!isInvokedFromDetailBottomSheet) return

        _state.update {
            it.copy(bottomSheetInfo = it.bottomSheetInfo?.copy(type = BottomSheetType.CHECK_REMOVE))
        }
    }

    override fun hideCheckLinkRemoveBottomSheet() {
        if (_state.value.bottomSheetInfo?.type != BottomSheetType.CHECK_REMOVE) return
        _state.update {
            it.copy(bottomSheetInfo = null)
        }
    }

    override fun removeLink(linkId: String) {
        val intLinkId = linkId.toIntOrNull() ?: return
        viewModelScope.launch {
            val response = deleteLinkUseCase.deleteLink(intLinkId)
            if (response is PokitResult.Success) {
                LinkUpdateEvent.removeSuccess(intLinkId)
            }
        }
    }
}
