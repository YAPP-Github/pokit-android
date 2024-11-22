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
class LinkListViewModel @Inject constructor(
    private val bookMarkContentsUseCase: BookMarkContentsUseCase,
    private val bookMarkContentsCountUseCase: BookMarkContentsCountUseCase,
    private val unReadContentsUseCase: UnReadContentsUseCase,
    private val unReadContentsCountUseCase: UnReadContentsCountUseCase,
    private val getLinkUseCase: GetLinkUseCase,
    private val deleteLinkUseCase: DeleteLinkUseCase,
    private val setBookmarkUseCase: SetBookmarkUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
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

    private val type: LinkListScreenType = savedStateHandle.get<String>("type")?.let { LinkListScreenType.getByKey(it) } ?: LinkListScreenType.Unread
    private val _state = MutableStateFlow(LinkListScreenState(sort = LinkSort.RECENT, type = type, count = 0))
    val state = _state.asStateFlow()

    private fun initLinkRemoveEventDetector() {
        viewModelScope.launch {
            LinkUpdateEvent.removedLink.collectLatest { removedLinkId ->
                val targetLink = linkPaging.pagingData.value.find { it.id == removedLinkId.toString() } ?: return@collectLatest
                linkPaging.deleteItem(targetLink.id)
            }
        }
    }

    private val linkPaging = SimplePaging(
        pagingSource = linkPagingSource,
        getKeyFromItem = { remindResult -> remindResult.id },
        coroutineScope = viewModelScope
    )

    val linkList: StateFlow<List<Link>> = linkPaging.pagingData
    val linkListState: StateFlow<PagingState> = linkPaging.pagingState

    init {
        viewModelScope.launch {
            linkPaging.refresh()
        }

        updateContentsCount()
        initLinkRemoveEventDetector()
    }

    fun toggleSort() {
        val sort = LinkSort.toggle(state.value.sort)
        viewModelScope.launch(Dispatchers.IO) {
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

    fun loadNextLinks() {
        viewModelScope.launch {
            linkPaging.load()
        }
    }

    fun showLinkDetailBottomSheet(link: Link) {
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

    fun hideLinkDetailBottomSheet() {
        if (_state.value.bottomSheetInfo?.type != BottomSheetType.DETAIL) return
        _state.update {
            it.copy(bottomSheetInfo = null)
        }
    }

    fun showCheckLinkRemoveBottomSheet() {
        val currentState = _state.value.copy()

        val isInvokedFromDetailBottomSheet = (currentState.bottomSheetInfo?.type == BottomSheetType.DETAIL)
        if (!isInvokedFromDetailBottomSheet) return

        _state.update {
            it.copy(bottomSheetInfo = it.bottomSheetInfo?.copy(type = BottomSheetType.CHECK_REMOVE))
        }
    }

    fun hideCheckLinkRemoveBottomSheet() {
        if (_state.value.bottomSheetInfo?.type != BottomSheetType.CHECK_REMOVE) return
        _state.update {
            it.copy(bottomSheetInfo = null)
        }
    }

    fun removeLink() {
        state.value.bottomSheetInfo?.link?.let { link ->
            val linkId = link.id.toInt()
            viewModelScope.launch {
                val response = deleteLinkUseCase.deleteLink(linkId)
                if (response is PokitResult.Success) {
                    LinkUpdateEvent.removeSuccess(linkId)
                }
            }
        }
    }

    fun toggleBookmark() {
        state.value.bottomSheetInfo?.link?.let { link ->
            val newBookmarkState = !link.bookmark
            val linkId = link.id.toIntOrNull() ?: return@let

            viewModelScope.launch {
                val response = setBookmarkUseCase.setBookMarked(linkId, newBookmarkState)
                if (response is PokitResult.Success) {
                    val bookmarkChangedLink = linkPaging.pagingData.value
                        .find { it.id == link.id }
                        ?.copy(bookmark = newBookmarkState) ?: return@launch
                    linkPaging.modifyItem(bookmarkChangedLink)

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
    }
}
