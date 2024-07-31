package pokitmons.pokit.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
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
import pokitmons.pokit.login.LoginScreen
import pokitmons.pokit.search.SearchScreenContainer
import pokitmons.pokit.search.SearchViewModel

@Composable
fun RootNavHost(
    navHostController: NavHostController,
) {
    NavHost(navController = navHostController, startDestination = AddPokit.routeWithArgs) {
        composable(Login.route) { navBackStackEntry ->
            val viewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                loginViewModel = viewModel,
                onNavigateToTermsOfServiceScreen = {}
            )
        }

        composable(Home.route) { navBackStackEntry ->
            Box(modifier = Modifier.fillMaxSize())
        }

        composable(
            route = AddLink.routeWithArgs,
            arguments = AddLink.arguments
        ) { navBackStackEntry ->
            val viewModel: AddLinkViewModel = hiltViewModel()
            val linkId = navBackStackEntry.arguments?.getString(AddLink.linkIdArg)
            AddLinkScreenContainer(
                linkId = linkId,
                viewModel = viewModel,
                onBackPressed = navHostController::popBackStack
            )
        }

        composable(
            route = AddPokit.routeWithArgs,
            arguments = AddPokit.arguments
        ) { navBackStackEntry ->
            val viewModel: AddPokitViewModel = hiltViewModel()
            val pokitId = navBackStackEntry.arguments?.getString(AddPokit.pokitIdArg)
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
                onBackPressed = navHostController::popBackStack
            )
        }

        composable(
            route = Search.route
        ) {
            val viewModel: SearchViewModel = hiltViewModel()
            SearchScreenContainer(
                viewModel = viewModel,
                onBackPressed = navHostController::popBackStack
            )
        }
    }
}
