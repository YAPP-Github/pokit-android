package pokitmons.pokit.settings.setting

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.template.bottomsheet.PokitBottomSheet
import pokitmons.pokit.core.ui.components.template.removeItemBottomSheet.TwoButtonBottomSheetContent
import pokitmons.pokit.settings.SettingViewModel
import pokitmons.pokit.settings.R.string as StringResource

@Composable
fun SettingsScreen(
    settingViewModel: SettingViewModel,
    onNavigateToEditNickname: () -> Unit,
) {
//    settingViewModel.changeBottomSheetHideState()

    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxWidth()) {
        SettingHeader()
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
                settingViewModel.changeBottomSheetHideState()
            }
            SettingItem(title = stringResource(StringResource.delete_account)) {
                settingViewModel.changeBottomSheetHideState()
            }
        }
    }

    // TODO 회원탈퇴 분기
    PokitBottomSheet(
        onHideBottomSheet = { },
        show = settingViewModel.isBottomSheetVisible
    ) {
        TwoButtonBottomSheetContent(
            subText = stringResource(id = StringResource.delete_account_sub),
            title = stringResource(id = StringResource.delete_account_title),
            rightButtonText = stringResource(id = StringResource.start_delete_account),
            onClickLeftButton = { settingViewModel.changeBottomSheetHideState() },
            onClickRightButton = {}
        )
    }
}

fun moveToUrl(url: String, context: Context) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}
