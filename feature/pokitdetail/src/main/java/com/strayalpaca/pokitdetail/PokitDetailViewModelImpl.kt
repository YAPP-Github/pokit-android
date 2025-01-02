package com.strayalpaca.pokitdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalpaca.pokitdetail.model.BottomSheetType
import com.strayalpaca.pokitdetail.model.Filter
import com.strayalpaca.pokitdetail.model.Link
import com.strayalpaca.pokitdetail.model.Pokit
import com.strayalpaca.pokitdetail.model.PokitDetailScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pokitmons.pokit.core.feature.flow.EventFlow
import pokitmons.pokit.core.feature.flow.MutableEventFlow
import pokitmons.pokit.core.feature.flow.asEventFlow
import pokitmons.pokit.core.feature.model.paging.PagingLoadResult
import pokitmons.pokit.core.feature.model.paging.PagingSource
import pokitmons.pokit.core.feature.model.paging.PagingState
import pokitmons.pokit.core.feature.model.paging.SimplePaging
import pokitmons.pokit.core.feature.navigation.args.LinkUpdateEvent
import pokitmons.pokit.core.feature.navigation.args.PokitUpdateEvent
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.link.LinksSort
import pokitmons.pokit.domain.usecase.link.DeleteLinkUseCase
import pokitmons.pokit.domain.usecase.link.GetLinkUseCase
import pokitmons.pokit.domain.usecase.link.GetLinksUseCase
import pokitmons.pokit.domain.usecase.link.SetBookmarkUseCase
import pokitmons.pokit.domain.usecase.pokit.DeletePokitUseCase
import pokitmons.pokit.domain.usecase.pokit.GetPokitUseCase
import pokitmons.pokit.domain.usecase.pokit.GetPokitsUseCase
import javax.inject.Inject
import kotlin.math.max

@HiltViewModel
class PokitDetailViewModelImpl @Inject constructor(
    private val getPokitsUseCase: GetPokitsUseCase,
    private val getLinksUseCase: GetLinksUseCase,
    private val getPokitUseCase: GetPokitUseCase,
    private val deletePokitUseCase: DeletePokitUseCase,
    private val deleteLinkUseCase: DeleteLinkUseCase,
    private val setBookmarkUseCase: SetBookmarkUseCase,
    private val getLinkUseCase: GetLinkUseCase,
    savedStateHandle: SavedStateHandle,
) : PokitDetailViewModel, ViewModel() {
    private val pokitPagingSource = object : PagingSource<Pokit> {
        override suspend fun load(pageIndex: Int, pageSize: Int): PagingLoadResult<Pokit> {
            val response = getPokitsUseCase.getPokits(size = pageSize, page = pageIndex)
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

    override val pokitList: StateFlow<List<Pokit>>
        get() = pokitPaging.pagingData
    override val pokitListState: StateFlow<PagingState>
        get() = pokitPaging.pagingState

    private val linkPagingSource = object : PagingSource<Link> {
        override suspend fun load(pageIndex: Int, pageSize: Int): PagingLoadResult<Link> {
            val currentPokit = state.value.currentPokit
            val currentFilter = state.value.currentFilter
            val categoryId = currentPokit?.id?.toIntOrNull() ?: savedStateHandle.get<String>("pokit_id")?.toIntOrNull() ?: 0
            val sort = if (currentFilter.recentSortUsed) LinksSort.RECENT else LinksSort.OLDER
            val response = getLinksUseCase.getLinks(
                page = pageIndex,
                size = pageSize,
                categoryId = categoryId,
                sort = sort,
                isRead = if (currentFilter.notReadChecked) false else null,
                favorite = if (currentFilter.bookmarkChecked) true else null
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

    override val linkList: StateFlow<List<Link>>
        get() = linkPaging.pagingData
    override val linkListState: StateFlow<PagingState>
        get() = linkPaging.pagingState

    private val _moveToBackEvent = MutableEventFlow<Boolean>()
    override val moveToBackEvent: EventFlow<Boolean>
        get() = _moveToBackEvent.asEventFlow()

    private val _state = MutableStateFlow(PokitDetailScreenState())
    override val state: StateFlow<PokitDetailScreenState>
        get() = _state.asStateFlow()

    init {
        val pokitId = savedStateHandle.get<String>("pokit_id")?.toIntOrNull()
        val linkCount = savedStateHandle.get<String>("pokit_count")?.toIntOrNull() ?: 0

        pokitId?.let { id ->
            viewModelScope.launch {
                linkPaging.refresh()
            }
            getPokit(id, linkCount)
        }

        initLinkAddEventDetector()
        initLinkUpdateEventDetector()
        initLinkRemoveEventDetector()
        initPokitUpdateEventDetector()
    }


    private fun initLinkAddEventDetector() {
        viewModelScope.launch {
            LinkUpdateEvent.addedLink.collectLatest { addedLink ->
                if (state.value.currentPokit?.id != addedLink.pokitId.toString()) return@collectLatest
                linkPaging.refresh()
            }
        }
    }

    private fun initLinkUpdateEventDetector() {
        viewModelScope.launch {
            LinkUpdateEvent.updatedLink.collectLatest { updatedLink ->
                val targetLink = linkPaging.pagingData.value.find { it.id == updatedLink.id.toString() } ?: return@collectLatest

                if (updatedLink.pokitId.toString() != targetLink.pokitId) {
                    linkPaging.deleteItem(targetLink.id)
                } else {
                    val modifiedLink = targetLink.copy(
                        title = updatedLink.title,
                        imageUrl = updatedLink.thumbnail,
                        domainUrl = updatedLink.domain,
                        createdAt = updatedLink.createdAt
                    )
                    linkPaging.modifyItem(modifiedLink)
                }
            }
        }
    }

    private fun initLinkRemoveEventDetector() {
        viewModelScope.launch {
            LinkUpdateEvent.removedLink.collectLatest { removedLinkId ->
                val targetLink = linkPaging.pagingData.value.find { it.id == removedLinkId.toString() } ?: return@collectLatest
                linkPaging.deleteItem(targetLink.id)

                val currentPokit = state.value.currentPokit ?: return@collectLatest
                val changedLinkCount = max(currentPokit.count - 1, 0)
                _state.update { it.copy(currentPokit = currentPokit.copy(count = changedLinkCount)) }
            }
        }
    }

    private fun initPokitUpdateEventDetector() {
        viewModelScope.launch {
            PokitUpdateEvent.updatedPokit.collectLatest { updatedPokit ->
                if (state.value.currentPokit?.id != updatedPokit.id.toString()) return@collectLatest

                val pokit = state.value.currentPokit?.copy(
                    title = updatedPokit.title
                ) ?: return@collectLatest
                _state.update { it.copy(currentPokit = pokit) }
            }
        }

        viewModelScope.launch {
            PokitUpdateEvent.countModifiedPokitIds.collectLatest { linkCountChangedPokitIds ->
                val currentPokit = state.value.currentPokit ?: return@collectLatest
                linkCountChangedPokitIds.decreasedPokitId?.let { targetId ->
                    if (targetId.toString() == currentPokit.id) {
                        val changedLinkCount = max(currentPokit.count - 1, 0)
                        _state.update { it.copy(currentPokit = currentPokit.copy(count = changedLinkCount)) }
                    }
                }
            }
        }
    }

    private fun getPokit(pokitId: Int, linkCount: Int) {
        viewModelScope.launch {
            val response = getPokitUseCase.getPokit(pokitId)
            if (response is PokitResult.Success) {
                _state.update { it.copy(currentPokit = Pokit.fromDomainPokit(response.result).copy(count = linkCount)) }
            }
        }
    }

    override fun changePokit(pokit: Pokit) {
        _state.update { it.copy(currentPokit = pokit, pokitSelectBottomSheetVisible = false) }
        viewModelScope.launch {
            linkPaging.refresh()
        }
    }

    override fun changeFilter(filter: Filter) {
        val currentFilter = state.value.currentFilter
        if (currentFilter == filter) {
            _state.update { it.copy(filterChangeBottomSheetVisible = false) }
            return
        }

        _state.update { it.copy(currentFilter = filter, filterChangeBottomSheetVisible = false) }
        viewModelScope.launch {
            linkPaging.refresh()
        }
    }

    override fun showPokitModifyBottomSheet() {
        state.value.currentPokit ?: return
        _state.update { it.copy(pokitBottomSheetType = BottomSheetType.MODIFY) }
    }

    override fun showPokitRemoveBottomSheet() {
        _state.update { it.copy(pokitBottomSheetType = BottomSheetType.REMOVE) }
    }

    override fun hidePokitBottomSheet() {
        _state.update { it.copy(pokitBottomSheetType = null) }
    }

    override fun showLinkRemoveBottomSheet() {
        _state.update { it.copy(linkBottomSheetType = BottomSheetType.REMOVE) }
    }

    override fun showLinkRemoveBottomSheet(link: Link) {
        _state.update { it.copy(linkBottomSheetType = BottomSheetType.REMOVE, currentLink = link) }
    }

    override fun hideLinkBottomSheet() {
        _state.update { it.copy(linkBottomSheetType = null, currentLink = null) }
    }

    override fun showLinkDetailBottomSheet(link: Link) {
        _state.update {
            it.copy(currentLink = link, linkDetailBottomSheetVisible = true)
        }

        viewModelScope.launch {
            val response = getLinkUseCase.getLink(link.id.toInt())
            if (response is PokitResult.Success && state.value.currentLink?.id == link.id && state.value.linkDetailBottomSheetVisible) {
                _state.update { it.copy(currentLink = Link.fromDomainLink(response.result).copy(imageUrl = link.imageUrl, isRead = true)) }
            }

            val isReadChangedLink = linkPaging.pagingData.value
                .find { it.id == link.id }
                ?.copy(isRead = true) ?: return@launch

            linkPaging.modifyItem(isReadChangedLink)
        }
    }

    override fun hideLinkDetailBottomSheet() {
        _state.update { it.copy(currentLink = null, linkDetailBottomSheetVisible = false) }
    }

    override fun showFilterChangeBottomSheet() {
        _state.update { it.copy(filterChangeBottomSheetVisible = true) }
    }

    override fun hideFilterChangeBottomSheet() {
        _state.update { it.copy(filterChangeBottomSheetVisible = false) }
    }

    override fun showPokitSelectBottomSheet() {
        _state.update { it.copy(pokitSelectBottomSheetVisible = true) }
    }

    override fun hidePokitSelectBottomSheet() {
        _state.update { it.copy(pokitSelectBottomSheetVisible = false) }
    }

    override fun loadNextPokits() {
        viewModelScope.launch {
            pokitPaging.load()
        }
    }

    override fun refreshPokits() {
        viewModelScope.launch {
            pokitPaging.refresh()
        }
    }

    override fun loadNextLinks() {
        viewModelScope.launch {
            linkPaging.load()
        }
    }

    override fun deletePokit(pokit: Pokit) {
        val pokitId = pokit.id.toIntOrNull() ?: return
        viewModelScope.launch {
            val response = deletePokitUseCase.deletePokit(pokitId)
            if (response is PokitResult.Success) {
                PokitUpdateEvent.removePokit(pokitId)
                _moveToBackEvent.emit(true)
            }
        }
    }

    override fun deleteLink(link: Link) {
        val linkId = link.id.toIntOrNull() ?: return
        viewModelScope.launch {
            val response = deleteLinkUseCase.deleteLink(linkId)
            if (response is PokitResult.Success) {
                LinkUpdateEvent.removeSuccess(linkId)
            }
        }
    }

    override fun toggleBookmark(link: Link) {
        val currentLinkId = link.id.toIntOrNull() ?: return
        val applyBookmarked = !link.bookmark

        viewModelScope.launch {
            val response = setBookmarkUseCase.setBookMarked(currentLinkId, applyBookmarked)
            if (response is PokitResult.Success) {
                val bookmarkChangedLink = linkPaging.pagingData.value
                    .find { it.id == link.id }
                    ?.copy(bookmark = applyBookmarked) ?: return@launch
                linkPaging.modifyItem(bookmarkChangedLink)

                if (link.id == state.value.currentLink?.id) {
                    _state.update { state ->
                        state.copy(
                            currentLink = bookmarkChangedLink
                        )
                    }
                }
            }
        }
    }
}
