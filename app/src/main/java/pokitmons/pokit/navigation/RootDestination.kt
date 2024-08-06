package pokitmons.pokit.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

object Login {
    val route: String = "login"
}

object Home {
    val route: String = "home"
}

object AddLink {
    val route: String = "addLink"
    val linkIdArg = "link_id"
    val routeWithArgs = "$route?$linkIdArg={$linkIdArg}"
    var arguments = listOf(
        navArgument(linkIdArg) {
            nullable = true
            type = NavType.StringType
        }
    )
}

object AddPokit {
    val route: String = "addPokit"
    val pokitIdArg = "pokit_id"
    val routeWithArgs = "$route?$pokitIdArg={$pokitIdArg}"
    var arguments = listOf(
        navArgument(pokitIdArg) {
            nullable = true
            type = NavType.StringType
        }
    )
}

object PokitDetail {
    val route: String = "pokitDetail"
    val pokitIdArg = "pokit_id"
    val routeWithArgs = "$route/{$pokitIdArg}"
    var arguments = listOf(navArgument(pokitIdArg) { defaultValue = "-" })
}

object Search {
    val route: String = "search"
}

object Setting {
    val route: String = "setting"
}

object EditNickname {
    val route: String = "editNickname"
}
