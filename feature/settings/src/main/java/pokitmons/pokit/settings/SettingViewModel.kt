package pokitmons.pokit.settings

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.usecase.auth.InputNicknameUseCase
import pokitmons.pokit.domain.usecase.setting.EditNicknameUseCase
import pokitmons.pokit.model.DuplicateNicknameState
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val nicknameUseCase: InputNicknameUseCase,
    private val editNicknameUseCase: EditNicknameUseCase,
) : ViewModel() {
    private var duplicateNicknameJob: Job? = null

    private val _isBottomSheetHide: MutableState<Boolean> = mutableStateOf(false)
    val isBottomSheetHide: Boolean
        get() = _isBottomSheetHide.value

    private val _inputNicknameState = MutableStateFlow(DuplicateNicknameState())
    val inputNicknameState: StateFlow<DuplicateNicknameState>
        get() = _inputNicknameState.asStateFlow()

    fun changeBottomSheetHideState() {
        _isBottomSheetHide.value = !_isBottomSheetHide.value
    }

    fun inputText(inputNickname: String) {
        _inputNicknameState.update { duplicateNicknameState ->
            duplicateNicknameState.copy(nickname = inputNickname)
        }
    }

    fun checkDuplicateNickname(nickname: String) {
        duplicateNicknameJob?.cancel()
        duplicateNicknameJob = viewModelScope.launch {
            delay(1.second())
            when (val duplicateNicknameResult = nicknameUseCase.checkDuplicateNickname(nickname)) {
                is PokitResult.Success -> {
                    _inputNicknameState.update { duplicateNicknameState ->
                        duplicateNicknameState.copy(isDuplicate = duplicateNicknameResult.result.isDuplicate)
                    }
                }

                is PokitResult.Error -> {}
            }
        }
    }

    fun editNickname() {
        Log.d("!! : ", "call")
        viewModelScope.launch {
            when (val editNicknameResult = editNicknameUseCase.editNickname(_inputNicknameState.value.nickname)) {
                is PokitResult.Success -> {
                    Log.d("!! : ", editNicknameResult.result.toString())
                }

                is PokitResult.Error -> {
                    Log.d("!! : ", editNicknameResult.error.toString())
                }
            }
        }
    }

    // TODO 확장함수 모듈 생성하기
    companion object {
        private fun Int.second(): Long {
            return (this * 1000L)
        }
    }
}
