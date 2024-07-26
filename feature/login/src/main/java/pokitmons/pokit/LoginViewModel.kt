package pokitmons.pokit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import pokitmons.pokit.domain.usecase.auth.SNSLoginUseCase
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: SNSLoginUseCase
) : ViewModel(), ContainerHost<LoginState, Nothing> {

    override val container: Container<LoginState, Nothing> = container(
        initialState = LoginState.Init,
    )

    fun snsLogin(authPlatform: String, idToken: String) = intent {
        viewModelScope.launch {
            loginUseCase.snsLogin(
                authPlatform = authPlatform,
                idToken = idToken
            ).onSuccess {  snsLoginResult ->
                accessToken = snsLoginResult.accessToken
                refreshToken = snsLoginResult.refreshToken
                reduce { LoginState.Login(snsLoginResult) }
            }.onFailure {  throwble ->
                reduce { LoginState.Error }
            }
        }
    }

    fun checkDuplicateNickname(nickname: String) {
        viewModelScope.launch {

        }
    }

    val categories: ArrayList<String> = arrayListOf()

    var accessToken: String = ""
        private set

    var refreshToken: String = ""
        private set

    var nickname: String = ""
        private set
}
