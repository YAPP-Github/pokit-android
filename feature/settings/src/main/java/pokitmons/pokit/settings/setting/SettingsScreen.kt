package pokitmons.pokit.settings.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.settings.R.string as StringResource

@Composable
fun SettingsScreen() {
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

            }
            SettingItem(title = stringResource(StringResource.delete_account)) {

            }
        }
    }
}
