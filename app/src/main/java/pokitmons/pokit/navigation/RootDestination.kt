package pokitmons.pokit.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

object Login {
    val route: String = "login"
}

object TermOfService {
    val route: String = "termOfService"
}

object InputNickname {
    val route: String = "inputNickname"
}

object SelectKeyword {
    val route: String = "selectKeyword"
}

object SignUpSuccess {
    val route: String = "signupSuccess"
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
    val pokitCountQuery = "pokit_count"
    val routeWithArgs = "$route/{$pokitIdArg}?$pokitCountQuery={$pokitCountQuery}"
    var arguments = listOf(
        navArgument(pokitIdArg) { defaultValue = "-" },
        navArgument(pokitCountQuery) {
            nullable = true
            type = NavType.StringType
        },
    )
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

object Alarm {
    val route: String = "alarm"
}
