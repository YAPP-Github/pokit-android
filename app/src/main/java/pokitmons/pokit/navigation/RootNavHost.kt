package pokitmons.pokit.navigation

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.strayalpaca.addlink.AddLinkScreenContainer
import com.strayalpaca.addlink.AddLinkViewModel
import com.strayalpaca.addpokit.AddPokitScreenContainer
import com.strayalpaca.addpokit.AddPokitViewModel
import com.strayalpaca.pokitdetail.PokitDetailScreenContainer
import com.strayalpaca.pokitdetail.PokitDetailViewModel
import pokitmons.pokit.LoginViewModel
import pokitmons.pokit.alarm.AlarmScreenContainer
import pokitmons.pokit.alarm.AlarmViewModel
import pokitmons.pokit.home.HomeScreen
import pokitmons.pokit.home.pokit.PokitViewModel
import pokitmons.pokit.keyword.KeywordScreen
import pokitmons.pokit.login.LoginScreen
import pokitmons.pokit.nickname.InputNicknameScreen
import pokitmons.pokit.search.SearchScreenContainer
import pokitmons.pokit.search.SearchViewModel
import pokitmons.pokit.settings.SettingViewModel
import pokitmons.pokit.settings.nickname.EditNicknameScreen
import pokitmons.pokit.settings.setting.SettingsScreen
import pokitmons.pokit.success.SignUpSuccessScreen
import pokitmons.pokit.terms.TermsOfServiceScreen

@Composable
fun RootNavHost(
    navHostController: NavHostController,
) {
    NavHost(
        modifier = Modifier.background(color = Color.White),
        navController = navHostController,
        startDestination = Login.route
    ) {
        composable(Login.route) {
            val viewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                loginViewModel = viewModel,
                onNavigateToTermsOfServiceScreen = { navHostController.navigate(TermOfService.route) },
                onNavigateToHomeScreen = {
                    navHostController.navigate(Home.route) {
                        popUpTo(navHostController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(TermOfService.route) {
            TermsOfServiceScreen(
                onNavigateToInputNicknameScreen = { navHostController.navigate(InputNickname.route) },
                onBackPressed = navHostController::popBackStack
            )
        }

        composable(InputNickname.route) {
            val viewModel: LoginViewModel = hiltViewModel()
            InputNicknameScreen(
                viewModel = viewModel,
                onNavigateToKeywordScreen = { navHostController.navigate(SelectKeyword.route) },
                onBackPressed = navHostController::popBackStack
            )
        }

        composable(SelectKeyword.route) {
            val viewModel: LoginViewModel = hiltViewModel()
            KeywordScreen(
                viewModel = viewModel,
                onNavigateToSignUpScreen = { navHostController.navigate(SignUpSuccess.route) },
                onBackPressed = navHostController::popBackStack
            )
        }

        composable(SignUpSuccess.route) {
            SignUpSuccessScreen(
                onNavigateToMainScreen = {
                    navHostController.navigate(Home.route) {
                        popUpTo(navHostController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = AddLink.routeWithArgs,
            arguments = AddLink.arguments
        ) {
            val viewModel: AddLinkViewModel = hiltViewModel()
            AddLinkScreenContainer(
                viewModel = viewModel,
                onBackPressed = navHostController::popBackStack,
                onNavigateToAddPokit = {
                    navHostController.navigate(AddPokit.route)
                }
            )
        }

        composable(
            route = AddPokit.routeWithArgs,
            arguments = AddPokit.arguments
        ) {
            val viewModel: AddPokitViewModel = hiltViewModel()
            AddPokitScreenContainer(
                viewModel = viewModel,
                onBackPressed = navHostController::popBackStack
            )
        }

        composable(
            route = PokitDetail.routeWithArgs,
            arguments = PokitDetail.arguments
        ) {
            val viewModel: PokitDetailViewModel = hiltViewModel()
            PokitDetailScreenContainer(
                viewModel = viewModel,
                onBackPressed = navHostController::popBackStack,
                onNavigateToLinkModify = { linkId ->
                    navHostController.navigate("${AddLink.route}?${AddLink.linkIdArg}=$linkId")
                },
                onNavigateToPokitModify = { pokitId ->
                    navHostController.navigate("${AddPokit.route}?${AddPokit.pokitIdArg}=$pokitId")
                }
            )
        }

        composable(
            route = Search.route
        ) {
            val viewModel: SearchViewModel = hiltViewModel()
            SearchScreenContainer(
                viewModel = viewModel,
                onBackPressed = navHostController::popBackStack,
                onNavigateToLinkModify = { linkId ->
                    navHostController.navigate("${AddLink.route}?${AddLink.linkIdArg}=$linkId")
                }
            )
        }

        composable(route = Setting.route) {
            val viewModel: SettingViewModel = hiltViewModel()
            SettingsScreen(
                settingViewModel = viewModel,
                onBackPressed = navHostController::popBackStack,
                onNavigateToEditNickname = { navHostController.navigate(EditNickname.route) },
                onNavigateToLogin = {
                    navHostController.navigate(Login.route) {
                        popUpTo(navHostController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = EditNickname.route) {
            val viewModel: SettingViewModel = hiltViewModel()
            EditNicknameScreen(
                settingViewModel = viewModel,
                onBackPressed = navHostController::popBackStack
            )
        }

        composable(route = Home.route) {
            val viewModel: PokitViewModel = hiltViewModel()
            HomeScreen(
                viewModel = viewModel,
                onNavigateToSearch = { navHostController.navigate(Search.route) },
                onNavigateToSetting = { navHostController.navigate(Setting.route) },
                onNavigateToPokitDetail = { pokitId, linkCount ->
                    navHostController.navigate(
                        "${PokitDetail.route}/$pokitId?${PokitDetail.pokitCountQuery}=$linkCount"
                    )
                },
                onNavigateAddLink = { navHostController.navigate("${AddLink.route}?${AddLink.linkUrl}=$it") },
                onNavigateAddPokit = { navHostController.navigate(AddPokit.route) },
                onNavigateToLinkModify = { navHostController.navigate("${AddLink.route}?${AddLink.linkIdArg}=$it") },
                onNavigateToPokitModify = { navHostController.navigate("${AddPokit.route}?${AddPokit.pokitIdArg}=$it") },
                onNavigateToAlarm = { navHostController.navigate(Alarm.route) }
            )
        }

        composable(route = Alarm.route) {
            val viewModel: AlarmViewModel = hiltViewModel()
            AlarmScreenContainer(
                viewModel = viewModel,
                onBackPressed = navHostController::popBackStack,
                onNavigateToLinkModify = { linkId ->
                    navHostController.navigate("${AddLink.route}?${AddLink.linkIdArg}=$linkId")
                }
            )
        }
    }
}
