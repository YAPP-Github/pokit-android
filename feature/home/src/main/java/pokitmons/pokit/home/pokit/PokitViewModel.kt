package pokitmons.pokit.home.pokit

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalpaca.pokitdetail.paging.PokitPaging
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pokitmons.pokit.domain.usecase.pokit.GetPokitsUseCase
import com.strayalpaca.pokitdetail.model.Pokit
import com.strayalpaca.pokitdetail.paging.LinkPaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.link.Link
import pokitmons.pokit.domain.model.link.LinksSort
import pokitmons.pokit.domain.usecase.link.GetLinksUseCase
import com.strayalpaca.pokitdetail.model.Link as DetailLink
import javax.inject.Inject

@HiltViewModel
class PokitViewModel @Inject constructor(
    private val getPokitsUseCase: GetPokitsUseCase,
    private val getLinksUseCase: GetLinksUseCase,
) : ViewModel() {

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

    private var _unCategoryLinks: MutableStateFlow<List<DetailLink>> = linkPaging._pagingData
    val unCategoryLinks: StateFlow<List<DetailLink>>
        get() = _unCategoryLinks.asStateFlow()

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
            sort = sort,
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
