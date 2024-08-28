package pokitmons.pokit.home.pokit

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalpaca.pokitdetail.model.BottomSheetType
import com.strayalpaca.pokitdetail.model.Pokit
import com.strayalpaca.pokitdetail.paging.LinkPaging
import com.strayalpaca.pokitdetail.paging.PokitPaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pokitmons.pokit.core.feature.flow.MutableEventFlow
import pokitmons.pokit.core.feature.flow.asEventFlow
import pokitmons.pokit.core.feature.navigation.args.LinkUpdateEvent
import pokitmons.pokit.core.feature.navigation.args.PokitUpdateEvent
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.link.Link
import pokitmons.pokit.domain.model.link.LinksSort
import pokitmons.pokit.domain.model.pokit.MAX_POKIT_COUNT
import pokitmons.pokit.domain.model.pokit.PokitsSort
import pokitmons.pokit.domain.usecase.link.DeleteLinkUseCase
import pokitmons.pokit.domain.usecase.link.GetLinksUseCase
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
) : ViewModel() {

    private val _sideEffect = MutableEventFlow<HomeSideEffect>()
    val sideEffect = _sideEffect.asEventFlow()

    private val _toastMessage = MutableStateFlow<HomeToastMessage?>(null)
    val toastMessage = _toastMessage.asStateFlow()

    private fun initLinkUpdateEventDetector() {
        viewModelScope.launch {
            LinkUpdateEvent.updatedLink.collectLatest { updatedLink ->
                val targetLink = linkPaging.pagingData.value.find { it.id == updatedLink.id.toString() } ?: return@collectLatest

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
            LinkUpdateEvent.removedLink.collectLatest { removedLink ->
                val targetLink = linkPaging.pagingData.value.find { it.id == removedLink.toString() } ?: return@collectLatest
                linkPaging.deleteItem(targetLink)
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
                val targetPokit = pokitPaging.pagingData.value.find { it.id == removedPokitId.toString() } ?: return@collectLatest
                pokitPaging.deleteItem(targetPokit)
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

    private val pokitPaging = PokitPaging(
        getPokits = getPokitsUseCase,
        perPage = 10,
        coroutineScope = viewModelScope,
        initPage = 0
    )

    private val linkPaging = LinkPaging(
        getLinks = ::getUncategorizedLinks,
        perPage = 10,
        coroutineScope = viewModelScope,
        initPage = 0,
        initCategoryId = 1
    )

    val pokits: StateFlow<List<Pokit>>
        get() = pokitPaging._pagingData.asStateFlow()

    val pokitsState = pokitPaging.pagingState

    val unCategoryLinks: StateFlow<List<DetailLink>>
        get() = linkPaging._pagingData.asStateFlow()

    val linksState = linkPaging.pagingState

    private val _currentDetailSelectedCategory = MutableStateFlow<Pokit?>(null)
    val currentDetailSelectedCategory = _currentDetailSelectedCategory.asStateFlow()

    private val _pokitOptionBottomSheetType = MutableStateFlow<BottomSheetType?>(null)
    val pokitOptionBottomSheetType = _pokitOptionBottomSheetType.asStateFlow()

    private val _currentSelectedLink = MutableStateFlow<DetailLink?>(null)
    val currentSelectedLink = _currentSelectedLink.asStateFlow()

    private val _currentDetailShowLink = MutableStateFlow<DetailLink?>(null)
    val currentDetailShowLink = _currentDetailShowLink.asStateFlow()

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
        sortPokits()
    }

    private fun sortPokits() {
        when (pokitsSortOrder.value) {
            is PokitsSortOrder.Name -> {
                pokitPaging.changeSort(PokitsSort.ALPHABETICAL)
            }
            is PokitsSortOrder.Latest -> {
                pokitPaging.changeSort(PokitsSort.RECENT)
            }
        }

        viewModelScope.launch {
            pokitPaging.refresh()
        }
    }

    fun updateLinksSortOrder(order: UncategorizedLinksSortOrder) {
        linksSortOrder.value = order
        sortUncategorizedLinks()
    }

    private fun sortUncategorizedLinks() {
        when (linksSortOrder.value) {
            is UncategorizedLinksSortOrder.Latest -> {
                linkPaging.changeOptions(0, LinksSort.RECENT)
            }
            is UncategorizedLinksSortOrder.Older -> {
                linkPaging.changeOptions(0, LinksSort.OLDER)
            }
        }

        viewModelScope.launch {
            linkPaging.refresh()
        }
    }

    private suspend fun getUncategorizedLinks(categoryId: Int, size: Int, page: Int, sort: LinksSort): PokitResult<List<Link>> {
        return getLinksUseCase.getUncategorizedLinks(
            size = size,
            page = page,
            sort = sort
        )
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
        _pokitOptionBottomSheetType.update { BottomSheetType.MODIFY }
        _currentSelectedLink.update { link }
    }

    fun hideLinkOptionBottomSheet() {
        _pokitOptionBottomSheetType.update { null }
        _currentSelectedLink.update { null }
    }

    fun showLinkRemoveBottomSheet() {
        _pokitOptionBottomSheetType.update { BottomSheetType.REMOVE }
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
    }

    fun hideDetailLinkBottomSheet() {
        _currentDetailShowLink.update { null }
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
