package pokitmons.pokit

sealed class LoginState {
    data object Init : LoginState()
    data object Login : LoginState()
    data object Failed : LoginState()
}

