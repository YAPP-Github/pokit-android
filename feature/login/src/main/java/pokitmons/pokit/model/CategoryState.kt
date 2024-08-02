package pokitmons.pokit.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class CategoryState(
    val name: String,
    var isSelected: MutableState<Boolean> = mutableStateOf(false)
)
