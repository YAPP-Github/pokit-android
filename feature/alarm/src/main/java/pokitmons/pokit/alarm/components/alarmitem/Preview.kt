package pokitmons.pokit.alarm.components.alarmitem

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import pokitmons.pokit.alarm.model.Alarm
import pokitmons.pokit.alarm.model.Date
import pokitmons.pokit.core.ui.theme.PokitTheme

@Preview(showBackground = true)
@Composable
internal fun Preview() {
    PokitTheme {
        Column {
            AlarmItem(alarm = Alarm(title = "자연 친화적인 라이프스타일을 위한 무언가"), onClickAlarm = {}, onClickRemove = {})
            AlarmItem(alarm = Alarm(title = "자연 친화적인 라이프스타일을 위한 무언가", read = true, createdAt = Date(year = 1969)), onClickAlarm = {}, onClickRemove = {})
            AlarmItem(alarm = Alarm(title = "자연 친화적인 라이프스타일을 위한 무언가", createdAt = Date(year = 1960)), onClickAlarm = {}, onClickRemove = {})
        }
    }
}
