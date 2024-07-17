package pokitmons.pokit.navigation

sealed class LoginRoute(val name: String) {
    data object LoginScreen : LoginRoute("LoginScreen")
    data object TermsOfServiceScreen : LoginRoute("TermsOfServiceScreen")
    data object InputNicknameScreen : LoginRoute("InputNicknameScreen")
    data object KeywordScreen : LoginRoute("KeywordScreen")
    data object SignUpSuccessScreen : LoginRoute("SignUpSuccessScreen")
}
