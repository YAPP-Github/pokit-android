package pokitmons.pokit.settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SettingViewModel : ViewModel() {
    private val _isBottomSheetHide: MutableState<Boolean> = mutableStateOf(false)
    val isBottomSheetHide: Boolean
        get() = _isBottomSheetHide.value

    fun changeBottomSheetHideState() {
        _isBottomSheetHide.value = !_isBottomSheetHide.value
    }
}
