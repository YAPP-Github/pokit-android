package pokitmons.pokit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.navigation.RootNavHost

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var showSplash by remember { mutableStateOf(true) }

            LaunchedEffect(Unit) {
                delay(1500)
                showSplash = false
            }

            PokitTheme {

                if (showSplash) {
                    SplashScreen()
                } else {
                    LoginNavHost()
                }

                val navHostController = rememberNavController()
                val navBackStackEntry by navHostController.currentBackStackEntryAsState()
                val currentDestination by remember(navBackStackEntry) { derivedStateOf { navBackStackEntry?.destination } }

                LaunchedEffect(currentDestination) {
                    currentDestination?.route?.let { route ->
                        // 믹스패널/파베 애널리틱스 화면 이동 로깅용
                    }
                }

                RootNavHost(navHostController = navHostController)

            }
        }
    }
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = pokitmons.pokit.core.ui.R.drawable.logo_white),
            contentDescription = null)
    }
}
