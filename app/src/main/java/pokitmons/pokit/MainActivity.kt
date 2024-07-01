package pokitmons.pokit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.login.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokitTheme {
                LoginScreen()
            }
        }
    }
}
