package pokitmons.pokit

import android.app.Application
import com.google.firebase.FirebaseApp
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PokitApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        KakaoSdk.init(this, "7890f93caf1d9d5da976da4b4bc6e5e7")
    }
}
