package pokitmons.pokit.login

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pokitmons.pokit.LoginState
import pokitmons.pokit.LoginViewModel
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitLoginButtonType
import pokitmons.pokit.core.ui.components.atom.loginbutton.PokitLoginButton
import pokitmons.pokit.core.ui.theme.PokitTheme

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    onNavigateToTermsOfServiceScreen: () -> Unit,
) {
    val loginState by loginViewModel.loginState.collectAsState()
    val context: Context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    when (loginState) {
        is LoginState.Init -> Unit
        is LoginState.Login -> onNavigateToTermsOfServiceScreen()
        is LoginState.Failed -> {
            // TODO 로그인 실패 바텀시트 렌더링
            Toast.makeText(context, (loginState as LoginState.Failed).error.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    // Column 자체가 가운데로 오게 하려면?
    Box(
        modifier = Modifier
            .background(color = PokitTheme.colors.backgroundBase)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Image(
                painter = painterResource(id = pokitmons.pokit.core.ui.R.drawable.logo_brand),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.login_sub_text),
                style = PokitTheme.typography.body1Bold
            )
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp)
                .align(Alignment.BottomCenter)
        ) {
            PokitLoginButton(
                loginType = PokitLoginButtonType.APPLE,
                text = stringResource(id = R.string.apple_login),
                onClick = {
                    appleLogin(
                        context = context,
                        snsLogin = loginViewModel::snsLogin
                    )
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            PokitLoginButton(
                loginType = PokitLoginButtonType.GOOGLE,
                text = stringResource(id = R.string.google_login),
                onClick = {
                    onNavigateToTermsOfServiceScreen()
                    googleLogin(
                        snsLogin = loginViewModel::snsLogin,
                        coroutineScope = coroutineScope,
                        context = context
                    )
                }
            )
        }
    }
}

private fun googleLogin(
    snsLogin: (String, String) -> Unit,
    coroutineScope: CoroutineScope,
    context: Context,
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
            snsLogin("구글", googleIdToken)
        } catch (e: Exception) {
            // TODO 로그인 실패 바텀시트 렌더링
        }
    }
}

private fun appleLogin(
    context: Context,
    snsLogin: (String, String) -> Unit,
) {
    val provider = OAuthProvider.newBuilder("apple.com").apply {
        addCustomParameter("locale", "ko")
    }

    // 이미 응답을 수신 했는지 확인
    val auth = FirebaseAuth.getInstance()
    val pending = auth.pendingAuthResult
    if (pending != null) {
        pending.addOnSuccessListener { authResult ->
            handleAuthResult(authResult, snsLogin)
        }.addOnFailureListener { e ->
            // TODO 로그인 실패 바텀시트 렌더링
        }
    } else {
        auth.startActivityForSignInWithProvider(context as Activity, provider.build()).addOnSuccessListener { authResult ->
            handleAuthResult(authResult, snsLogin)
        }.addOnFailureListener {
            // TODO 로그인 실패 바텀시트 렌더링
        }
    }
}

private fun handleAuthResult(
    authResult: AuthResult,
    snsLogin: (String, String) -> Unit,
) {
    val user = authResult.user
    user?.getIdToken(true)?.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val idToken = task.result?.token
            if (idToken != null) {
                snsLogin("애플", idToken)
            } else {
                // TODO 로그인 실패 바텀시트 렌더링
            }
        } else {
            // TODO 로그인 실패 바텀시트 렌더링
        }
    }
}
