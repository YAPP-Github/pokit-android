package pokitmons.pokit

import pokitmons.pokit.domain.model.auth.SNSLogin

sealed class LoginState {
        data object Init : LoginState()
        data class Login(val loginResponse: SNSLogin) : LoginState()
//        data class CheckDuplicateNickname(val checkDuplicateNicknameResponse: CheckDuplicateNickname)
        data object Error : LoginState()
    }
