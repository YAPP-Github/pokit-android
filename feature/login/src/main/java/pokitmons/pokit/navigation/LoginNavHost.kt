package pokitmons.pokit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pokitmons.pokit.keyword.KeywordScreen
import pokitmons.pokit.login.LoginScreen
import pokitmons.pokit.nickname.InputNicknameScreen
import pokitmons.pokit.success.SignUpSuccessScreen
import pokitmons.pokit.terms.TermsOfServiceScreen

@Composable
fun LoginNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = LoginRoute.LoginScreen.name
    ) {
        composable(route = LoginRoute.LoginScreen.name) {
            LoginScreen(
                onNavigateToTermsOfServiceScreen = { navController.navigate(route = LoginRoute.TermsOfServiceScreen.name) },
                onNavigateToMainScreen = {  }, // TODO 메인 화면 구현후 수정
            )
        }

        composable(route = LoginRoute.TermsOfServiceScreen.name) {
            TermsOfServiceScreen(
                onNavigateToInputNicknameScreen = { navController.navigate(route = LoginRoute.InputNicknameScreen.name) },
            )
        }

        composable(route = LoginRoute.InputNicknameScreen.name) {
            InputNicknameScreen(
                onNavigateToKeywordScreen = { navController.navigate(route = LoginRoute.KeywordScreen.name) },
            )
        }

        composable(route = LoginRoute.KeywordScreen.name) {
            KeywordScreen(
                onNavigateToSignUpScreen = { navController.navigate(route = LoginRoute.SignUpSuccessScreen.name) },
            )
        }

        composable(route = LoginRoute.SignUpSuccessScreen.name) {
            SignUpSuccessScreen(
                onNavigateToMainScreen = {  }, // TODO 메인 화면 구현후 수정
            )
        }
    }
}
