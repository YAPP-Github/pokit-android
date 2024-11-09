package pokitmons.pokit.linklist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pokitmons.pokit.core.feature.model.paging.PagingLoadResult
import pokitmons.pokit.core.feature.model.paging.PagingSource
import pokitmons.pokit.core.feature.model.paging.PagingState
import pokitmons.pokit.core.feature.model.paging.SimplePaging
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.home.remind.RemindResult
import pokitmons.pokit.domain.usecase.home.remind.BookMarkContentsCountUseCase
import pokitmons.pokit.domain.usecase.home.remind.BookMarkContentsUseCase
import pokitmons.pokit.domain.usecase.home.remind.UnReadContentsCountUseCase
import pokitmons.pokit.domain.usecase.home.remind.UnReadContentsUseCase
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
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val linkPagingSource = object : PagingSource<Link> {
        override suspend fun load(pageIndex: Int, pageSize: Int): PagingLoadResult<Link> {
            val response: PokitResult<List<RemindResult>> = if (state.value.type == LinkListScreenType.Bookmark) {
                bookMarkContentsUseCase.getBookmarkContents()
            } else {
                unReadContentsUseCase.getUnreadContents()
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

    private val linkPaging = SimplePaging(
        pagingSource = linkPagingSource,
        getKeyFromItem = { remindResult -> remindResult.id },
        coroutineScope = viewModelScope
    )

    val linkList: StateFlow<List<Link>> = linkPaging.pagingData
    val linkListState: StateFlow<PagingState> = linkPaging.pagingState

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
}
