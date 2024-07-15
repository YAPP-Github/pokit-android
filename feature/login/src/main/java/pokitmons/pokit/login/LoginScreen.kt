package pokitmons.pokit.login

import android.annotation.SuppressLint
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
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.launch
import pokitmons.pokit.core.ui.components.atom.button.PokitLoginButton
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitLoginButtonType

@Composable
fun LoginScreen(
    onNavigateToTermsOfServiceScreen: () -> Unit,
    onNavigateToMainScreen: () -> Unit,
) {
    // TODO 서버 api 개발완료 후 viewmodel 연동 및 아키텍처 구축
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = 20.dp, end = 20.dp, bottom = 32.dp),
        ) {
            PokitLoginButton(
                loginType = PokitLoginButtonType.APPLE,
                text = stringResource(id = R.string.apple_login),
                onClick = { onNavigateToMainScreen() },
            )

            Spacer(modifier = Modifier.height(8.dp))

            PokitLoginButton(
                loginType = PokitLoginButtonType.GOOGLE,
                text = stringResource(id = R.string.google_login),
                onClick = { onNavigateToTermsOfServiceScreen() },
            )
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
private fun googleLogin() {
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
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
            Log.d("success: ", googleIdToken)
        } catch (e: Exception) {
            Log.d("failed : ", e.message.toString())
        }
    }
}
