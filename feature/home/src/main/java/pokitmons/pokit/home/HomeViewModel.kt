package pokitmons.pokit.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    var selectedCategory = mutableStateOf<Category>(Category.Pokit)
        private set

    var sortOrder = mutableStateOf<SortOrder>(SortOrder.Latest)
        private set

    var screenType = mutableStateOf<ScreenType>(ScreenType.Pokit)
        private set

    fun updateCategory(category: Category) {
        selectedCategory.value = category
    }

    fun updateSortOrder(order: SortOrder) {
        sortOrder.value = order
    }

    fun updateScreenType(type: ScreenType) {
        screenType.value = type
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
