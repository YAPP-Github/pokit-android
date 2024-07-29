package pokitmons.pokit

import pokitmons.pokit.domain.commom.PokitError

sealed class LoginState {
    data object Init : LoginState()
    data object Login : LoginState()
    data class Failed(val error: PokitError) : LoginState()
}

