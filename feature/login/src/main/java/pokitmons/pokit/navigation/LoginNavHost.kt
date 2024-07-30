package pokitmons.pokit.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pokitmons.pokit.LoginViewModel
import pokitmons.pokit.keyword.KeywordScreen
import pokitmons.pokit.login.LoginScreen
import pokitmons.pokit.nickname.InputNicknameScreen
import pokitmons.pokit.success.SignUpSuccessScreen
import pokitmons.pokit.terms.TermsOfServiceScreen

@Composable
fun LoginNavHost() {
    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = LoginRoute.LoginScreen.name
    ) {
        composable(route = LoginRoute.LoginScreen.name) {
            LoginScreen(
                loginViewModel = loginViewModel,
                onNavigateToTermsOfServiceScreen = { navController.navigate(route = LoginRoute.TermsOfServiceScreen.name) }
            )
        }

        composable(route = LoginRoute.TermsOfServiceScreen.name) {
            TermsOfServiceScreen(
                onNavigateToInputNicknameScreen = { navController.navigate(route = LoginRoute.InputNicknameScreen.name) },
                popBackStack = { navController.popBackStack() }
            )
        }

        composable(route = LoginRoute.InputNicknameScreen.name) {
            InputNicknameScreen(
                loginViewModel = loginViewModel,
                onNavigateToKeywordScreen = { navController.navigate(route = LoginRoute.KeywordScreen.name) },
                popBackStack = { navController.popBackStack() }
            )
        }

        composable(route = LoginRoute.KeywordScreen.name) {
            KeywordScreen(
                loginViewModel = loginViewModel,
                onNavigateToSignUpScreen = { navController.navigate(route = LoginRoute.SignUpSuccessScreen.name) },
                popBackStack = { navController.popBackStack() }
            )
        }

        composable(route = LoginRoute.SignUpSuccessScreen.name) {
            SignUpSuccessScreen(
                loginViewModel = loginViewModel,
                onNavigateToMainScreen = { } // TODO 메인 화면 구현후 수정
            )
        }
    }
}
