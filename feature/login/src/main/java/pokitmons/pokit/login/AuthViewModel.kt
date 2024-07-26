package pokitmons.pokit.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pokitmons.pokit.domain.usecase.auth.SNSLoginUseCase
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: SNSLoginUseCase
) : ViewModel() {
    fun test(idToken: String) {
        viewModelScope.launch {
            runCatching {
                loginUseCase.snsLogin(
                    idToken = idToken,
                    authPlatform = "구글"
                )
            }.onSuccess {
                Log.d("!! 성공 : ", it.toString())
            }.onFailure {
                Log.d("!! 실패 : ", it.toString())
            }
        }
    }
}
