package pokitmons.pokit.settings.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.template.bottomsheet.PokitBottomSheet
import pokitmons.pokit.core.ui.components.template.removeItemBottomSheet.TwoButtonBottomSheetContent
import pokitmons.pokit.settings.SettingViewModel
import pokitmons.pokit.settings.R.string as StringResource

@Composable
fun SettingsScreen(
    settingViewModel: SettingViewModel
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        SettingHeader()
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            SettingItem(title = stringResource(StringResource.nickname_settings)) {

            }
            SettingItem(title = stringResource(StringResource.notification_settings)) {

            }

            DividerItem()

            SettingItem(title = stringResource(StringResource.announcements)) {

            }
            SettingItem(title = stringResource(StringResource.terms_of_service)) {

            }
            SettingItem(title = stringResource(StringResource.privacy_policy)) {

            }
            SettingItem(title = stringResource(StringResource.customer_support)) {

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
        show = settingViewModel.isBottomSheetHide
    ) {
        TwoButtonBottomSheetContent(
            title = stringResource(id = StringResource.logout_title),
            rightButtonText = stringResource(id = StringResource.logout),
            onClickLeftButton = { settingViewModel.changeBottomSheetHideState() },
            onClickRightButton = {}
        )
    }
}
