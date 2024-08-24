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
import pokitmons.pokit.core.feature.navigation.args.LinkUpdateEvent
import pokitmons.pokit.core.feature.navigation.args.PokitUpdateEvent
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.link.Link
import pokitmons.pokit.domain.model.link.LinksSort
import pokitmons.pokit.domain.usecase.link.GetLinksUseCase
import pokitmons.pokit.domain.usecase.pokit.DeletePokitUseCase
import pokitmons.pokit.domain.usecase.pokit.GetPokitsUseCase
import javax.inject.Inject
import com.strayalpaca.pokitdetail.model.Link as DetailLink
import pokitmons.pokit.domain.model.pokit.Pokit as DomainPokit

@HiltViewModel
class PokitViewModel @Inject constructor(
    private val getPokitsUseCase: GetPokitsUseCase,
    private val getLinksUseCase: GetLinksUseCase,
    private val deletePokitUseCase: DeletePokitUseCase,
) : ViewModel() {

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
                val linkAddedPokit = pokitPaging.pagingData.value.find { it.id == addedLink.pokitId.toString() } ?: return@collectLatest
                val modifiedPokit = linkAddedPokit.copy(count = (linkAddedPokit.count + 1))
                pokitPaging.modifyItem(modifiedPokit)
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

    var sortOrder = mutableStateOf<SortOrder>(SortOrder.Latest)
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

    private var _pokits: MutableStateFlow<List<Pokit>> = pokitPaging._pagingData
    val pokits: StateFlow<List<Pokit>>
        get() = _pokits.asStateFlow()

    val pokitsState = pokitPaging.pagingState

    private var _unCategoryLinks: MutableStateFlow<List<DetailLink>> = linkPaging._pagingData
    val unCategoryLinks: StateFlow<List<DetailLink>>
        get() = _unCategoryLinks.asStateFlow()

    val linksState = linkPaging.pagingState

    private val _currentDetailSelectedCategory = MutableStateFlow<Pokit?>(null)
    val currentDetailSelectedCategory = _currentDetailSelectedCategory.asStateFlow()

    private val _pokitOptionBottomSheetType = MutableStateFlow<BottomSheetType?>(null)
    val pokitOptionBottomSheetType = _pokitOptionBottomSheetType.asStateFlow()

    init {
        initLinkUpdateEventDetector()
        initPokitUpdateEventDetector()
        initPokitRemoveEventDetector()
        initLinkAddEventDetector()
        initPokitAddEventDetector()
    }

    fun updateCategory(category: Category) {
        selectedCategory.value = category
    }

    fun updateSortOrder(order: SortOrder) {
        sortOrder.value = order
        sortPokits()
    }

    private fun sortPokits() {
        when (sortOrder.value) {
            is SortOrder.Name -> {
                _pokits.update { pokit ->
                    pokit.sortedBy { pokitDetail ->
                        pokitDetail.title
                    }
                }
            }
            is SortOrder.Latest -> {
                _pokits.update { pokit ->
                    pokit.sortedByDescending { pokitDetail ->
                        pokitDetail.createdAt
                    }
                }
            }
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
            pokitPaging.load()
        }
    }

    fun loadUnCategoryLinks() {
        viewModelScope.launch {
            linkPaging.load()
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
}

sealed class Category {
    data object Pokit : Category()
    data object Unclassified : Category()
}

sealed class SortOrder {
    data object Latest : SortOrder()
    data object Name : SortOrder()
}

sealed class ScreenType {
    data object Pokit : ScreenType()
    data object Remind : ScreenType()
}
