package pokitmons.pokit

import pokitmons.pokit.domain.commom.PokitError

sealed class LoginState {
    data object Init : LoginState()
    data object Login : LoginState()
    data object Registered : LoginState()
    data object AutoLogin : LoginState()
    data class Failed(val error: PokitError) : LoginState()
}

sealed class SignUpState {
    data object Init : SignUpState()
    data object SignUp : SignUpState()
    data class Failed(val error: PokitError) : SignUpState()
}
