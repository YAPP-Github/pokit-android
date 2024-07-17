package pokitmons.pokit.nickname

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class InputNicknameViewModel : ViewModel() {
    private val _inputNicknameState = MutableStateFlow("")
    val inputNicknameState: StateFlow<String>
        get() = _inputNicknameState

    fun inputText(text: String) {
        _inputNicknameState.value = text
    }
}
