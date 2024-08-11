package pokitmons.pokit.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    var selectedCategory = mutableStateOf<Category>(Category.Pokit)
        private set

    // 정렬 순서 상태 관리
    var sortOrder = mutableStateOf<SortOrder>(SortOrder.Latest)
        private set

    fun updateCategory(category: Category) {
        selectedCategory.value = category
    }

    fun updateSortOrder(order: SortOrder) {
        sortOrder.value = order
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
