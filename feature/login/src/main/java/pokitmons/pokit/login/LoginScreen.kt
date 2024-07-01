package pokitmons.pokit.login

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LoginScreen() {
    // TODO 서버 api 개발완료 후 viewmodel 연동 및 아키텍처 구축

    Text(text = "로그인 테스트 화면")
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
