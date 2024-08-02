package pokitmons.pokit

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
import pokitmons.pokit.domain.usecase.auth.SNSLoginUseCase
import pokitmons.pokit.model.DuplicateNicknameState
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: SNSLoginUseCase,
    private val nicknameUseCase: InputNicknameUseCase
) : ViewModel() {

    private var duplicateNicknameJob: Job? = null

    private val _loginState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Init)
    val loginState: StateFlow<LoginState>
        get() = _loginState.asStateFlow()

    private val _inputNicknameState = MutableStateFlow(DuplicateNicknameState())
    val inputNicknameState: StateFlow<DuplicateNicknameState>
        get() = _inputNicknameState.asStateFlow()

    fun inputText(inputNickname: String) {
        _inputNicknameState.update { duplicateNicknameState ->
            duplicateNicknameState.copy(nickname = inputNickname)
        }
    }

    fun snsLogin(authPlatform: String, idToken: String) {
        viewModelScope.launch {
            val loginResult = loginUseCase.snsLogin(
                authPlatform = authPlatform,
                idToken = idToken
            )

            when (loginResult) {
                is PokitResult.Success -> {
                    accessToken = loginResult.result.accessToken
                    refreshToken = loginResult.result.refreshToken
                    _loginState.emit(LoginState.Login)
                }
                is PokitResult.Error -> _loginState.emit(LoginState.Failed(loginResult.error))
            }
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

    val categories: ArrayList<String> = arrayListOf()

    var accessToken: String = ""
        private set

    var refreshToken: String = ""
        private set

    // TODO 확장함수 모듈 생성하기
    companion object {
        private fun Int.second(): Long {
            return (this * 1000L)
        }
    }
}
