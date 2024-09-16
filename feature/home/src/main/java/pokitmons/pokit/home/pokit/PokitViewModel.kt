package pokitmons.pokit.home.pokit

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalpaca.pokitdetail.model.BottomSheetType
import com.strayalpaca.pokitdetail.model.Pokit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pokitmons.pokit.core.feature.flow.MutableEventFlow
import pokitmons.pokit.core.feature.flow.asEventFlow
import pokitmons.pokit.core.feature.model.paging.PagingLoadResult
import pokitmons.pokit.core.feature.model.paging.PagingSource
import pokitmons.pokit.core.feature.model.paging.SimplePaging
import pokitmons.pokit.core.feature.navigation.args.LinkUpdateEvent
import pokitmons.pokit.core.feature.navigation.args.PokitUpdateEvent
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.link.LinksSort
import pokitmons.pokit.domain.model.pokit.MAX_POKIT_COUNT
import pokitmons.pokit.domain.model.pokit.PokitsSort
import pokitmons.pokit.domain.usecase.link.DeleteLinkUseCase
import pokitmons.pokit.domain.usecase.link.GetLinkUseCase
import pokitmons.pokit.domain.usecase.link.GetLinksUseCase
import pokitmons.pokit.domain.usecase.link.SetBookmarkUseCase
import pokitmons.pokit.domain.usecase.pokit.DeletePokitUseCase
import pokitmons.pokit.domain.usecase.pokit.GetPokitCountUseCase
import pokitmons.pokit.domain.usecase.pokit.GetPokitsUseCase
import pokitmons.pokit.home.model.HomeSideEffect
import pokitmons.pokit.home.model.HomeToastMessage
import javax.inject.Inject
import com.strayalpaca.pokitdetail.model.Link as DetailLink
import pokitmons.pokit.domain.model.pokit.Pokit as DomainPokit

@HiltViewModel
class PokitViewModel @Inject constructor(
    private val getPokitsUseCase: GetPokitsUseCase,
    private val getLinksUseCase: GetLinksUseCase,
    private val deletePokitUseCase: DeletePokitUseCase,
    private val getPokitCountUseCase: GetPokitCountUseCase,
    private val deleteLinkUseCase: DeleteLinkUseCase,
    private val setBookmarkUseCase: SetBookmarkUseCase,
    private val getLinkUseCase: GetLinkUseCase,
) : ViewModel() {

    private val _sideEffect = MutableEventFlow<HomeSideEffect>()
    val sideEffect = _sideEffect.asEventFlow()

    private val _toastMessage = MutableStateFlow<HomeToastMessage?>(null)
    val toastMessage = _toastMessage.asStateFlow()

    private fun initLinkUpdateEventDetector() {
        viewModelScope.launch {
            LinkUpdateEvent.updatedLink.collectLatest { updatedLink ->
                val targetLink = linkPaging.pagingData.value.find { it.id == updatedLink.id.toString() } ?: return@collectLatest

                val isCategoryChanged = (targetLink.pokitId != updatedLink.pokitId.toString())
                if (isCategoryChanged) {
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

    private fun initLinkAddEventDetector() {
        viewModelScope.launch {
            LinkUpdateEvent.addedLink.collectLatest { addedLink ->
                linkPaging.refresh()
                val linkAddedPokit = pokitPaging.pagingData.value.find { it.id == addedLink.pokitId.toString() } ?: return@collectLatest
                val modifiedPokit = linkAddedPokit.copy(count = (linkAddedPokit.count + 1))
                pokitPaging.modifyItem(modifiedPokit)
            }
        }
    }

    private fun initLinkRemoveEventDetector() {
        viewModelScope.launch {
            LinkUpdateEvent.removedLink.collectLatest { removedLinkId ->
                linkPaging.deleteItem(removedLinkId.toString())
            }
        }
    }

    private fun initPokitUpdateEventDetector() {
        viewModelScope.launch {
            PokitUpdateEvent.updatedPokit.collectLatest { updatedPokit ->
                val targetPokit = pokitPaging.pagingData.value.find { it.id == updatedPokit.id.toString() } ?: return@collectLatest
                val pokitImage = DomainPokit.Image(id = updatedPokit.imageId, url = updatedPokit.imageUrl)
                val modifiedPokit = targetPokit.copy(title = updatedPokit.title, image = pokitImage)
                pokitPaging.modifyItem(modifiedPokit)
            }
        }
    }

    private fun initPokitRemoveEventDetector() {
        viewModelScope.launch {
            PokitUpdateEvent.removedPokitId.collectLatest { removedPokitId ->
                pokitPaging.deleteItem(removedPokitId.toString())
            }
        }
    }

    private fun initPokitAddEventDetector() {
        viewModelScope.launch {
            PokitUpdateEvent.addedPokit.collectLatest {
                pokitPaging.refresh()
            }
        }
    }

    var selectedCategory = mutableStateOf<Category>(Category.Pokit)
        private set

    var pokitsSortOrder = mutableStateOf<PokitsSortOrder>(PokitsSortOrder.Latest)
        private set

    var linksSortOrder = mutableStateOf<UncategorizedLinksSortOrder>(UncategorizedLinksSortOrder.Latest)
        private set

    var screenType = mutableStateOf<ScreenType>(ScreenType.Pokit)
        private set

    private val pokitPagingSource = object: PagingSource<Pokit> {
        override suspend fun load(pageIndex: Int, pageSize: Int): PagingLoadResult<Pokit> {
            val sort = when (pokitsSortOrder.value) {
                PokitsSortOrder.Latest -> PokitsSort.RECENT
                PokitsSortOrder.Name -> PokitsSort.ALPHABETICAL
            }
            val response = getPokitsUseCase.getPokits(size = pageSize, page = pageIndex, sort = sort)
            return PagingLoadResult.fromPokitResult(
                pokitResult = response,
                mapper = { domainPokits -> domainPokits.map { Pokit.fromDomainPokit(it) }}
            )
        }
    }

    private val pokitPaging = SimplePaging(
        pagingSource = pokitPagingSource,
        getKeyFromItem = { pokit -> pokit.id },
        coroutineScope = viewModelScope
    )

    private val linksPagingSource = object: PagingSource<DetailLink> {
        override suspend fun load(pageIndex: Int, pageSize: Int): PagingLoadResult<DetailLink> {
            val sort = when(linksSortOrder.value) {
                UncategorizedLinksSortOrder.Latest -> LinksSort.RECENT
                UncategorizedLinksSortOrder.Older -> LinksSort.OLDER
            }
            val response = getLinksUseCase.getUncategorizedLinks(size = pageSize, page = pageIndex, sort = sort)
            return PagingLoadResult.fromPokitResult(
                pokitResult = response,
                mapper = { domainLinks -> domainLinks.map { DetailLink.fromDomainLink(it) } }
            )
        }
    }

    private val linkPaging = SimplePaging(
        pagingSource = linksPagingSource,
        getKeyFromItem = { link -> link.id },
        coroutineScope = viewModelScope
    )

    val pokits: StateFlow<List<Pokit>>
        get() = pokitPaging.pagingData

    val pokitsState = pokitPaging.pagingState

    val unCategoryLinks: StateFlow<List<DetailLink>>
        get() = linkPaging.pagingData

    val linksState = linkPaging.pagingState

    private val _currentDetailSelectedCategory = MutableStateFlow<Pokit?>(null)
    val currentDetailSelectedCategory = _currentDetailSelectedCategory.asStateFlow()

    private val _pokitOptionBottomSheetType = MutableStateFlow<BottomSheetType?>(null)
    val pokitOptionBottomSheetType = _pokitOptionBottomSheetType.asStateFlow()

    private val _currentSelectedLink = MutableStateFlow<DetailLink?>(null)
    val currentSelectedLink = _currentSelectedLink.asStateFlow()

    private val _currentDetailShowLink = MutableStateFlow<DetailLink?>(null)
    val currentDetailShowLink = _currentDetailShowLink.asStateFlow()

    private val _linkOptionBottomSheetType = MutableStateFlow<BottomSheetType?>(null)
    val linkOptionBottomSheetType = _linkOptionBottomSheetType.asStateFlow()

    init {
        initLinkUpdateEventDetector()
        initPokitUpdateEventDetector()
        initPokitRemoveEventDetector()
        initLinkAddEventDetector()
        initPokitAddEventDetector()
        initLinkRemoveEventDetector()

        loadUnCategoryLinks()
        loadPokits()
    }

    fun updateCategory(category: Category) {
        selectedCategory.value = category
    }

    fun updatePokitsSortOrder(order: PokitsSortOrder) {
        pokitsSortOrder.value = order
        viewModelScope.launch {
            pokitPaging.refresh()
        }
    }

    fun updateLinksSortOrder(order: UncategorizedLinksSortOrder) {
        linksSortOrder.value = order
        viewModelScope.launch {
            linkPaging.refresh()
        }
    }

    fun updateScreenType(type: ScreenType) {
        screenType.value = type
    }

    fun loadPokits() {
        viewModelScope.launch {
            pokitPaging.refresh()
        }
    }

    fun loadUnCategoryLinks() {
        viewModelScope.launch {
            linkPaging.refresh()
        }
    }

    fun showPokitDetailOptionBottomSheet(pokit: Pokit) {
        _currentDetailSelectedCategory.update { pokit }
        _pokitOptionBottomSheetType.update { BottomSheetType.MODIFY }
    }

    fun showPokitDetailRemoveBottomSheet() {
        _pokitOptionBottomSheetType.update {
            BottomSheetType.REMOVE
        }
    }

    fun hidePokitDetailRemoveBottomSheet() {
        _currentDetailSelectedCategory.update { null }
        _pokitOptionBottomSheetType.update { null }
    }

    fun removeCurrentDetailSelectedCategory() {
        viewModelScope.launch {
            val currentDetailSelectedPokit = currentDetailSelectedCategory.value ?: return@launch
            val pokitId = currentDetailSelectedPokit.id.toInt()
            val response = deletePokitUseCase.deletePokit(pokitId)
            if (response is PokitResult.Success) {
                PokitUpdateEvent.removePokit(pokitId)
            }
        }
    }

    fun checkPokitCount() {
        viewModelScope.launch {
            _toastMessage.update { null }
            val response = getPokitCountUseCase.getPokitCount()
            if (response is PokitResult.Success) {
                if (response.result >= MAX_POKIT_COUNT) {
                    _toastMessage.update { HomeToastMessage.CANNOT_CREATE_POKIT_MORE }
                } else {
                    _sideEffect.emit(HomeSideEffect.NavigateToAddPokit)
                }
            }
        }
    }

    fun closeToastMessage() {
        _toastMessage.update { null }
    }

    fun showLinkOptionBottomSheet(link: DetailLink) {
        _linkOptionBottomSheetType.update { BottomSheetType.MODIFY }
        _currentSelectedLink.update { link }
    }

    fun hideLinkOptionBottomSheet() {
        _linkOptionBottomSheetType.update { null }
        _currentSelectedLink.update { null }
    }

    fun showLinkRemoveBottomSheet() {
        _linkOptionBottomSheetType.update { BottomSheetType.REMOVE }
    }

    fun showLinkRemoveBottomSheet(link: DetailLink) {
        _currentSelectedLink.update { link }
        _linkOptionBottomSheetType.update { BottomSheetType.REMOVE }
    }

    fun removeCurrentSelectedLink() {
        val currentSelectedLinkId = currentSelectedLink.value?.id?.toIntOrNull() ?: return
        viewModelScope.launch {
            val response = deleteLinkUseCase.deleteLink(currentSelectedLinkId)
            if (response is PokitResult.Success) {
                LinkUpdateEvent.removeSuccess(currentSelectedLinkId)
            }
        }
    }

    fun showDetailLinkBottomSheet(link: DetailLink) {
        _currentDetailShowLink.update { link }

        viewModelScope.launch {
            val response = getLinkUseCase.getLink(link.id.toInt())
            if (response is PokitResult.Success) {
                val responseLink = response.result
                if (_currentDetailShowLink.value?.id == responseLink.id.toString()) {
                    _currentDetailShowLink.update {
                        DetailLink(
                            id = responseLink.id.toString(),
                            title = responseLink.title,
                            dateString = responseLink.createdAt,
                            url = responseLink.data,
                            isRead = true,
                            domainUrl = responseLink.domain,
                            imageUrl = _currentDetailShowLink.value?.imageUrl,
                            memo = responseLink.memo,
                            bookmark = responseLink.favorites,
                            pokitName = responseLink.categoryName
                        )
                    }
                }
                val isReadChangedLink = linkPaging.pagingData.value
                    .find { it.id == link.id }
                    ?.copy(isRead = true) ?: return@launch

                linkPaging.modifyItem(isReadChangedLink)
            }
        }
    }

    fun hideDetailLinkBottomSheet() {
        _currentDetailShowLink.update { null }
    }

    fun toggleBookmark() {
        val currentLink = _currentDetailShowLink.value ?: return
        val currentLinkId = currentLink.id.toIntOrNull() ?: return
        val applyBookmarked = !currentLink.bookmark

        viewModelScope.launch {
            val response = setBookmarkUseCase.setBookMarked(currentLinkId, applyBookmarked)
            if (response is PokitResult.Success) {
                val bookmarkChangedLink = currentLink.copy(bookmark = applyBookmarked)
                linkPaging.modifyItem(bookmarkChangedLink)

                if (currentLink.id == _currentDetailShowLink.value?.id) {
                    _currentDetailShowLink.update { bookmarkChangedLink }
                }
            }
        }
    }
}

sealed class Category {
    data object Pokit : Category()
    data object Unclassified : Category()
}

sealed class PokitsSortOrder {
    data object Latest : PokitsSortOrder()
    data object Name : PokitsSortOrder()
}

sealed class UncategorizedLinksSortOrder {
    data object Latest : UncategorizedLinksSortOrder()
    data object Older : UncategorizedLinksSortOrder()
}

sealed class ScreenType {
    data object Pokit : ScreenType()
    data object Remind : ScreenType()
}
