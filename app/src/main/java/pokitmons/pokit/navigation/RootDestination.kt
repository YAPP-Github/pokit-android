package pokitmons.pokit.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface RootDestination {
    val route: String
}

object Login : RootDestination {
    override val route: String = "login"
}

object Home : RootDestination {
    override val route: String = "home"
}

object AddLink : RootDestination {
    override val route: String = "addLink"
    const val linkIdArg = "link_id"
    val routeWithArgs = "$route/{$linkIdArg}"
    var arguments = listOf(
        navArgument(linkIdArg) {
            nullable = true
            type = NavType.StringType
        }
    )
}

object AddPokit : RootDestination {
    override val route: String = "addPokit"
    const val pokitIdArg = "pokit_id"
    val routeWithArgs = "${AddLink.route}/{$pokitIdArg}"
    var arguments = listOf(
        navArgument(pokitIdArg) {
            nullable = true
            type = NavType.StringType
        }
    )
}

object PokitDetail : RootDestination {
    override val route: String = "pokitDetail"
    const val pokitIdArg = "pokit_id"
    val routeWithArgs = "${AddLink.route}/{${AddPokit.pokitIdArg}}"
    var arguments = listOf(navArgument(AddPokit.pokitIdArg) { nullable = true })
}

object Search : RootDestination {
    override val route: String = "search"
}
