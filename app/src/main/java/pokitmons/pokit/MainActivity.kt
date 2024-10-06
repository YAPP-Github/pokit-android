package pokitmons.pokit

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import pokitmons.pokit.core.feature.flow.collectAsEffect
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.navigation.AddLink
import pokitmons.pokit.navigation.RootNavHost

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleSharedLinkIntent(intent)
        setContent {
            var showSplash by remember { mutableStateOf(true) }

            LaunchedEffect(Unit) {
                delay(1500)
                showSplash = false
            }

            val navHostController = rememberNavController()
            val navBackStackEntry by navHostController.currentBackStackEntryAsState()
            val currentDestination by remember(navBackStackEntry) { derivedStateOf { navBackStackEntry?.destination } }

            viewModel.navigationEvent.collectAsEffect { navigationEvent ->
                if (navigationEvent is NavigationEvent.AddLink)
                    navHostController.navigate("${AddLink.route}?${AddLink.linkUrl}=${navigationEvent.url}")
            }

            PokitTheme {
                if (showSplash) {
                    SplashScreen()
                } else {
                    RootNavHost(navHostController = navHostController)
                }

                LaunchedEffect(currentDestination) {
                    currentDestination?.route?.let { route ->
                        viewModel.setCurrentRoute(route)
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleSharedLinkIntent(intent)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if (hasFocus) {
            val clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            clipboardManager.primaryClip?.let { clipData ->
                val setClipDataSuccess = viewModel.setClipData(clipData)
                if (!setClipDataSuccess) return@let
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    clipboardManager.clearPrimaryClip()
                } else {
                    val emptyClip = ClipData.newPlainText("", "")
                    clipboardManager.setPrimaryClip(emptyClip)
                }
            }
        }
    }

    private fun handleSharedLinkIntent(intent: Intent) {
        val action = intent.action ?: return

        val isSharedLinkData = (action == Intent.ACTION_SEND && intent.type == "text/plain")
        if (isSharedLinkData) {
            intent.getStringExtra(Intent.EXTRA_TEXT)?.let { url ->
                viewModel.setSharedLinkUrl(url)
            }
        }
    }
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .background(color = PokitTheme.colors.brandBold)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = pokitmons.pokit.core.ui.R.drawable.logo_white),
            contentDescription = null
        )
    }
}
