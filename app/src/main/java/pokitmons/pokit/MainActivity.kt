package pokitmons.pokit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.home.BottomNavigationBar
import pokitmons.pokit.home.HomeScreen
import pokitmons.pokit.navigation.RootNavHost

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokitTheme {
                HomeScreen()
//                val navHostController = rememberNavController()
//                val navBackStackEntry by navHostController.currentBackStackEntryAsState()
//                val currentDestination by remember(navBackStackEntry) { derivedStateOf { navBackStackEntry?.destination } }
//
//                LaunchedEffect(currentDestination) {
//                    currentDestination?.route?.let { route ->
//                        // 믹스패널/파베 애널리틱스 화면 이동 로깅용
//                    }
//                }
//
//                RootNavHost(navHostController = navHostController)
            }
        }
    }
}

