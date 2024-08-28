package pokitmons.pokit

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.usecase.auth.InputNicknameUseCase
import pokitmons.pokit.domain.usecase.auth.SNSLoginUseCase
import pokitmons.pokit.domain.usecase.auth.SignUpUseCase
import pokitmons.pokit.domain.usecase.auth.TokenUseCase
import pokitmons.pokit.login.R
import pokitmons.pokit.model.CategoryState
import pokitmons.pokit.model.DuplicateNicknameState
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val loginUseCase: SNSLoginUseCase,
    private val nicknameUseCase: InputNicknameUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val tokenUseCase: TokenUseCase,
) : ViewModel() {
    init {
        viewModelScope.launch {
            if (tokenUseCase.getAuthType().first() == "구글") {
                _loginState.emit(LoginState.AutoLogin)
            }
        }
    }

    val nicknameRegex = Regex("^[a-zA-Z0-9가-힣]+$")

    private var duplicateNicknameJob: Job? = null

    private val _isBottomSheetVisible: MutableState<Boolean> = mutableStateOf(false)
    val isBottomSheetVisible: Boolean
        get() = _isBottomSheetVisible.value

    private val _loginState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Init)
    val loginState: StateFlow<LoginState>
        get() = _loginState.asStateFlow()

    private val _signUpState: MutableStateFlow<SignUpState> = MutableStateFlow(SignUpState.Init)
    val signUpState: StateFlow<SignUpState>
        get() = _signUpState.asStateFlow()

    private val _inputNicknameState = MutableStateFlow(DuplicateNicknameState())
    val inputNicknameState: StateFlow<DuplicateNicknameState>
        get() = _inputNicknameState.asStateFlow()

    private val _categories = mutableStateListOf<CategoryState>()
    val categories: List<CategoryState> get() = _categories

    private var authType = "구글"

    fun inputText(inputNickname: String) {
        _inputNicknameState.update { duplicateNicknameState ->
            duplicateNicknameState.copy(nickname = inputNickname)
        }
    }

    fun snsLogin(authPlatform: String, idToken: String) {
        authType = authPlatform

        viewModelScope.launch {
            val loginResult = loginUseCase.snsLogin(
                authPlatform = authPlatform,
                idToken = idToken
            )

            when (loginResult) {
                is PokitResult.Success -> {
                    authType = authPlatform
                    when (loginResult.result.isRegistered) {
                        true -> {
                            tokenUseCase.apply {
                                setAccessToken(loginResult.result.accessToken)
                                setRefreshToken(loginResult.result.refreshToken)
                                setAuthType(authType)
                            }
                            _loginState.emit(LoginState.Registered)
                        }

                        false -> {
                            tokenUseCase.apply {
                                setAccessToken(loginResult.result.accessToken)
                                setRefreshToken(loginResult.result.refreshToken)
                            }
                            _loginState.emit(LoginState.Login)
                        }
                    }
                }

                is PokitResult.Error -> _loginState.emit(LoginState.Failed(loginResult.error))
            }
        }
    }

    fun signUp() {
        viewModelScope.launch {
            when (
                val signUpResult = signUpUseCase.signUp(
                    nickname = _inputNicknameState.value.nickname,
                    categories = _categories
                        .filter { category -> category.isSelected.value }
                        .map { categoryState -> categoryState.name }
                )
            ) {
                is PokitResult.Success -> {
                    tokenUseCase.setAuthType(authType)
                    _signUpState.emit(SignUpState.SignUp)
                }

                is PokitResult.Error -> {
                    _signUpState.emit(SignUpState.Failed(signUpResult.error))
                }
            }
        }
    }

    fun checkDuplicateNickname(nickname: String) {
        duplicateNicknameJob?.cancel()
        duplicateNicknameJob = viewModelScope.launch {
            delay(0.5.second())
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

    fun checkNicknameRegex(nickname: String): Boolean {
        _inputNicknameState.update { duplicateNicknameState ->
            duplicateNicknameState.copy(isRegex = nicknameRegex.matches(nickname))
        }
        return nicknameRegex.matches(nickname)
    }

    fun setCategories() {
        val categoryNames: List<String> = listOf(
            context.getString(R.string.sports_and_leisure),
            context.getString(R.string.phrases_and_office),
            context.getString(R.string.fashion),
            context.getString(R.string.travel),
            context.getString(R.string.economy_and_politics),
            context.getString(R.string.movies_and_dramas),
            context.getString(R.string.restaurants),
            context.getString(R.string.interior),
            context.getString(R.string.it),
            context.getString(R.string.design),
            context.getString(R.string.self_development),
            context.getString(R.string.humor),
            context.getString(R.string.music),
            context.getString(R.string.job_info)
        )

        _categories.clear()
        _categories.addAll(categoryNames.map { name -> CategoryState(name = name) })
    }

    fun onClickCategoryItem(category: CategoryState) {
        if (category.isSelected.value || isLimitSelected()) {
            category.isSelected.value = !category.isSelected.value
        }
    }

    private fun isLimitSelected(): Boolean {
        return _categories.count { it.isSelected.value } < LIMIT_SELECTED_COUNT
    }

    fun changeState() {
        viewModelScope.launch {
            _loginState.emit(LoginState.Init)
        }
    }

    fun setVisible(visible: Boolean) {
        _isBottomSheetVisible.value = visible
    }

    // TODO 확장함수 모듈 생성하기
    companion object {
        private fun Int.second(): Long {
            return (this * 1000L)
        }

        private fun Double.second(): Long {
            return (this * 1000L).toLong()
        }

        private const val LIMIT_SELECTED_COUNT = 3
    }
}
