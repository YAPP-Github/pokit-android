package pokitmons.pokit.login

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import pokitmons.pokit.LoginState
import pokitmons.pokit.LoginViewModel
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitLoginButtonType
import pokitmons.pokit.core.ui.components.atom.loginbutton.PokitLoginButton

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    onNavigateToTermsOfServiceScreen: () -> Unit,
) {
    val loginState: LoginState = loginViewModel.collectAsState().value
    val context: Context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // TODO : 리팩토링
    when (loginState) {
        is LoginState.Init -> Unit
        is LoginState.Login -> onNavigateToTermsOfServiceScreen()
        else -> onNavigateToTermsOfServiceScreen()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = 20.dp, end = 20.dp, bottom = 32.dp)
        ) {
            PokitLoginButton(
                loginType = PokitLoginButtonType.APPLE,
                text = stringResource(id = R.string.apple_login),
                onClick = {  }
            )

            Spacer(modifier = Modifier.height(8.dp))

            PokitLoginButton(
                loginType = PokitLoginButtonType.GOOGLE,
                text = stringResource(id = R.string.google_login),
                onClick = {
                    googleLogin(
                        loginViewModel = loginViewModel,
                        coroutineScope = coroutineScope,
                        context = context
                    )
                }
            )
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
private fun googleLogin(
    loginViewModel: LoginViewModel,
    coroutineScope: CoroutineScope,
    context: Context
) {

    val credentialManager = CredentialManager.create(context)

    val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(BuildConfig.WEB_CLIENT_ID)
        .setAutoSelectEnabled(true)
        .build()

    val request: GetCredentialRequest = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    coroutineScope.launch {
        try {
            val result = credentialManager.getCredential(
                request = request,
                context = context
            )
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(result.credential.data)
            val googleIdToken = googleIdTokenCredential.idToken

            loginViewModel.snsLogin(
                authPlatform = "구글",
                idToken = googleIdToken
            )
        } catch (e: Exception) {
            Log.d("failed!!: ", e.message.toString())
        }
    }
}
