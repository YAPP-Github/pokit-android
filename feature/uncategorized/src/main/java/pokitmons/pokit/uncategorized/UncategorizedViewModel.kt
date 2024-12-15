package pokitmons.pokit.uncategorized

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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
import pokitmons.pokit.domain.usecase.link.DeleteLinkUseCase
import pokitmons.pokit.domain.usecase.link.GetLinksUseCase
import pokitmons.pokit.domain.usecase.link.ModifyPokitOfLinksUseCase
import pokitmons.pokit.domain.usecase.pokit.GetPokitsUseCase
import pokitmons.pokit.uncategorized.model.Pokit
import pokitmons.pokit.uncategorized.model.UncategorizedLink
import pokitmons.pokit.uncategorized.model.UncategorizedScreenState
import javax.inject.Inject

interface UncategorizedViewModel {
    fun toggleLinkSelected(linkId: String)
    fun toggleAllLinksSelected()
    fun removeSelectedLinks()
    fun showPokitSelectBottomSheet()
    fun hidePokitSelectBottomSheet()
    fun moveSelectedLinks(pokitId: String)

    fun loadNextLinks()
    fun refreshPokits()
    fun loadNextPokits()

    val state: StateFlow<UncategorizedScreenState>
    val linkList: StateFlow<List<UncategorizedLink>>
    val linkListState: StateFlow<PagingState>
    val pokitList: StateFlow<List<Pokit>>
    val pokitListState: StateFlow<PagingState>
    val linkChanged: Boolean
}

@HiltViewModel
class UncategorizedViewModelImpl @Inject constructor(
    private val useCaseGetLinks: GetLinksUseCase,
    private val getPokitsUseCase: GetPokitsUseCase,
    private val modifyPokitOfLinksUseCase: ModifyPokitOfLinksUseCase,
    private val deleteLinkUseCase: DeleteLinkUseCase,
) : ViewModel(), UncategorizedViewModel {
    // uncategorized links paging
    private val linksPagingSource = object : PagingSource<UncategorizedLink> {
        override suspend fun load(pageIndex: Int, pageSize: Int): PagingLoadResult<UncategorizedLink> {
            val response = useCaseGetLinks.getUncategorizedLinks(size = pageSize, page = pageIndex)
            val checked = state.value.selectAll
            return PagingLoadResult.fromPokitResult(
                pokitResult = response,
                mapper = { links -> links.map { link -> UncategorizedLink(link = link, isChecked = checked) } }
            )
        }
    }

    private val linkPaging = SimplePaging(
        pagingSource = linksPagingSource,
        getKeyFromItem = { uncategorizedLink -> uncategorizedLink.link.id },
        coroutineScope = viewModelScope
    )

    override val linkList: StateFlow<List<UncategorizedLink>> = linkPaging.pagingData
    override val linkListState: StateFlow<PagingState> = linkPaging.pagingState

    // pokits paging
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

    override val pokitList: StateFlow<List<Pokit>> = pokitPaging.pagingData
    override val pokitListState: StateFlow<PagingState> = pokitPaging.pagingState

    // state
    private val _state = MutableStateFlow(UncategorizedScreenState(selectAll = false, updateLoading = false))
    override val state: StateFlow<UncategorizedScreenState> = _state.asStateFlow()

    private var _linkChanged = false
    override val linkChanged: Boolean
        get() = _linkChanged

    init {
        viewModelScope.launch {
            linkPaging.refresh()
        }
    }

    override fun toggleLinkSelected(linkId: String) {
        val targetLink = linkPaging.pagingData.value.find { it.link.id.toString() == linkId } ?: return
        val updatedTargetLink = targetLink.copy(isChecked = !targetLink.isChecked)

        linkPaging.modifyItem(updatedTargetLink)
    }

    override fun toggleAllLinksSelected() {
        if (linkPaging.pagingData.value.isEmpty()) return

        val selected = !state.value.selectAll

        _state.update { it.copy(selectAll = selected) }

        val updatedLinkList = linkPaging.pagingData.value.map { it.copy(isChecked = selected) }
        linkPaging.updateItems(updatedLinkList)
    }

    override fun removeSelectedLinks() {
        val selectedLinkIds = linkPaging.pagingData.value.filter { it.isChecked }.map { it.link.id }
        if (selectedLinkIds.isEmpty()) return

        viewModelScope.launch {
            _state.update { it.copy(updateLoading = true) }
            val response = deleteLinkUseCase.deleteUncategorizedLinks(linkIds = selectedLinkIds)
            if (response is PokitResult.Success) {
                _linkChanged = true
                linkPaging.refresh()
            }
            _state.update { it.copy(updateLoading = false) }
        }
    }

    override fun showPokitSelectBottomSheet() {
        val selectedLinkIds = linkPaging.pagingData.value.filter { it.isChecked }.map { it.link.id }
        if (selectedLinkIds.isEmpty()) return

        _state.update { it.copy(showPokitSelectBottomSheet = true) }
    }

    override fun hidePokitSelectBottomSheet() {
        _state.update { it.copy(showPokitSelectBottomSheet = false) }
    }

    override fun moveSelectedLinks(pokitId: String) {
        val selectedLinkIds = linkPaging.pagingData.value.filter { it.isChecked }.map { it.link.id }
        if (selectedLinkIds.isEmpty()) return

        viewModelScope.launch {
            _state.update { it.copy(showPokitSelectBottomSheet = false, updateLoading = true) }
            val response = modifyPokitOfLinksUseCase.modifyPokit(linkIds = selectedLinkIds, categoryId = pokitId.toInt())
            if (response is PokitResult.Success) {
                _linkChanged = true
                linkPaging.refresh()
            }
            _state.update { it.copy(updateLoading = false) }
        }
    }

    override fun loadNextLinks() {
        viewModelScope.launch {
            linkPaging.load()
        }
    }

    override fun refreshPokits() {
        viewModelScope.launch {
            pokitPaging.refresh()
        }
    }

    override fun loadNextPokits() {
        viewModelScope.launch {
            pokitPaging.load()
        }
    }
}
