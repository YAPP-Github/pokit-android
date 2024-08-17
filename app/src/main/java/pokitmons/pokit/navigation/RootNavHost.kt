package pokitmons.pokit.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
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
import pokitmons.pokit.home.HomeScreen
import pokitmons.pokit.home.pokit.PokitViewModel
import pokitmons.pokit.login.LoginScreen
import pokitmons.pokit.navigation.PokitDetail.pokitIdArg
import pokitmons.pokit.search.SearchScreenContainer
import pokitmons.pokit.search.SearchViewModel
import pokitmons.pokit.settings.SettingViewModel
import pokitmons.pokit.settings.nickname.EditNicknameScreen
import pokitmons.pokit.settings.setting.SettingsScreen

@Composable
fun RootNavHost(
    navHostController: NavHostController,
) {
    NavHost(navController = navHostController, startDestination = Home.route) {
        composable(Login.route) {
            val viewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                loginViewModel = viewModel,
                onNavigateToTermsOfServiceScreen = {}
            )
        }

        composable(Home.route) {
            Box(modifier = Modifier.fillMaxSize())
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
                onBackPressed = navHostController::popBackStack,
                onBackWithModifySuccess = { modifiedPokitId ->
                    navHostController.popBackStack()
                    navHostController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("modified_pokit_id", modifiedPokitId)
                }
            )
        }

        composable(
            route = PokitDetail.routeWithArgs,
            arguments = PokitDetail.arguments
        ) {
            val viewModel: PokitDetailViewModel = hiltViewModel()
            LaunchedEffect(it) {
                val pokitId = navHostController.currentBackStackEntry?.savedStateHandle?.get<Int>("modified_pokit_id") ?: return@LaunchedEffect
                viewModel.getPokit(pokitId)
            }

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
                onNavigateToEditNickname = { navHostController.navigate(EditNickname.route) }
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
                onNavigateToPokitDetail = { navHostController.navigate("${PokitDetail.route}/$it") },
                onNavigateAddLink = { navHostController.navigate(AddLink.route) },
                onNavigateAddPokit = { navHostController.navigate(AddPokit.route) }
            )
        }
    }
}
