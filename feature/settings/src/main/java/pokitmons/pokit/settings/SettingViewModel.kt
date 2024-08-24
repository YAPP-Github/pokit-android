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
import pokitmons.pokit.domain.usecase.auth.TokenUseCase
import pokitmons.pokit.domain.usecase.auth.WithdrawUseCase
import pokitmons.pokit.domain.usecase.setting.EditNicknameUseCase
import pokitmons.pokit.model.DuplicateNicknameState
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val nicknameUseCase: InputNicknameUseCase,
    private val editNicknameUseCase: EditNicknameUseCase,
    private val withdrawUseCase: WithdrawUseCase,
    private val tokenUseCase: TokenUseCase,
) : ViewModel() {
    private var duplicateNicknameJob: Job? = null

    private val _isBottomSheetVisible: MutableState<Boolean> = mutableStateOf(false)
    val isBottomSheetVisible: Boolean
        get() = _isBottomSheetVisible.value

    private val _withdrawState: MutableStateFlow<SettingState> = MutableStateFlow(SettingState.Init)
    val withdrawState: StateFlow<SettingState>
        get() = _withdrawState.asStateFlow()

    private val _inputNicknameState = MutableStateFlow(DuplicateNicknameState())
    val inputNicknameState: StateFlow<DuplicateNicknameState>
        get() = _inputNicknameState.asStateFlow()

    fun changeBottomSheetHideState(isShowing: Boolean) {
        _isBottomSheetVisible.value = isShowing
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
        viewModelScope.launch {
            when (val editNicknameResult = editNicknameUseCase.editNickname(_inputNicknameState.value.nickname)) {
                is PokitResult.Success -> {
                }

                is PokitResult.Error -> {
                }
            }
        }
    }

    fun withdraw() {
        Log.d("!! : ", "withdraw")
        viewModelScope.launch {
            when (val withdraw = withdrawUseCase.withdraw()) {
                is PokitResult.Success -> {
                    tokenUseCase.setAuthType("")
                    tokenUseCase.setAccessToken("")
                    _withdrawState.emit(SettingState.Withdraw)
                }

                is PokitResult.Error -> {
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

sealed class SettingState {
    data object Init : SettingState()
    data object Withdraw : SettingState()
}
