package pokitmons.pokit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.keyword.KeywordScreen
import pokitmons.pokit.navigation.LoginNavHost
import pokitmons.pokit.nickname.InputNicknameScreen
import pokitmons.pokit.success.SignUpSuccessScreen
import pokitmons.pokit.terms.TermsOfServiceScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokitTheme {
                LoginNavHost()
            }
        }
    }
}
