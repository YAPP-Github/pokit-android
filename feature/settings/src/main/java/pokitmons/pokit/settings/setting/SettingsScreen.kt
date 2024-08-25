package pokitmons.pokit.settings.setting

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.template.bottomsheet.PokitBottomSheet
import pokitmons.pokit.core.ui.components.template.removeItemBottomSheet.TwoButtonBottomSheetContent
import pokitmons.pokit.settings.SettingState
import pokitmons.pokit.settings.SettingViewModel
import pokitmons.pokit.settings.R.string as StringResource

@Composable
fun SettingsScreen(
    settingViewModel: SettingViewModel,
    onNavigateToEditNickname: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onBackPressed: () -> Unit,
) {
    val context = LocalContext.current

    val withdrawState by settingViewModel.withdrawState.collectAsState()

    when (withdrawState) {
        is SettingState.Init -> Unit
        is SettingState.Withdraw -> onNavigateToLogin()
        is SettingState.Logout -> onNavigateToLogin()
        is SettingState.Error -> Unit
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        SettingHeader(onBackPressed)
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            SettingItem(title = stringResource(StringResource.nickname_settings)) {
                onNavigateToEditNickname()
            }
            SettingItem(title = stringResource(StringResource.notification_settings)) {
                // TODO 커스텀 토스트 메세지
            }

            DividerItem()

            SettingItem(title = stringResource(StringResource.announcements)) {
                moveToUrl(url = "https://www.notion.so/POKIT-d97c81534b354cfebe677fbf1fbfe2b2", context)
            }
            SettingItem(title = stringResource(StringResource.terms_of_service)) {
                moveToUrl(url = "https://www.notion.so/3bddcd6fd00043abae6b92a50c39b132?pvs=4", context)
            }
            SettingItem(title = stringResource(StringResource.privacy_policy)) {
                moveToUrl(url = "https://www.notion.so/de3468b3be1744538c22a333ae1d0ec8", context)
            }
            SettingItem(title = stringResource(StringResource.customer_support)) {
                moveToUrl(url = "https://www.instagram.com/pokit.official/", context)
            }

            DividerItem()

            SettingItem(title = stringResource(StringResource.logout)) {
                settingViewModel.apply {
                    setType("로그아웃")
                    changeBottomSheetHideState(true)
                }
            }
            SettingItem(title = stringResource(StringResource.delete_account)) {
                settingViewModel.apply {
                    setType("회원탈퇴")
                    changeBottomSheetHideState(true)
                }
            }
        }
    }

    // TODO 리팩토링
    PokitBottomSheet(
        onHideBottomSheet = { settingViewModel.changeBottomSheetHideState(false) },
        show = settingViewModel.isBottomSheetVisible
    ) {
        TwoButtonBottomSheetContent(
            subText = if (settingViewModel.type.value == "회원탈퇴") stringResource(id = StringResource.delete_account_sub) else null,
            title = stringResource(id = if (settingViewModel.type.value == "회원탈퇴") StringResource.delete_account_title else StringResource.logout_title),
            rightButtonText = stringResource(id = if (settingViewModel.type.value == "회원탈퇴") StringResource.start_delete_account else StringResource.logout),
            onClickLeftButton = { settingViewModel.changeBottomSheetHideState(false) },
            onClickRightButton = { if (settingViewModel.type.value == "회원탈퇴") settingViewModel.withdraw() else settingViewModel.logout() }
        )
    }
}

fun moveToUrl(url: String, context: Context) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}
