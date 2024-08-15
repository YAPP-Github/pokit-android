package pokitmons.pokit.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalpaca.pokitdetail.paging.PokitPaging
import com.strayalpaca.pokitdetail.paging.SimplePagingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.usecase.pokit.GetPokitUseCase
import pokitmons.pokit.domain.usecase.pokit.GetPokitsUseCase
import com.strayalpaca.pokitdetail.model.Pokit
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPokitsUseCase: GetPokitsUseCase
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

    val pokits: StateFlow<List<Pokit>> = pokitPaging.pagingData

    fun updateCategory(category: Category) {
        selectedCategory.value = category
    }

    fun updateSortOrder(order: SortOrder) {
        sortOrder.value = order
    }

    fun updateScreenType(type: ScreenType) {
        screenType.value = type
    }

    fun loadPokits() {
        viewModelScope.launch {
            pokitPaging.load()
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
