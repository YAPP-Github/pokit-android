package pokitmons.pokit.alarm

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import pokitmons.pokit.alarm.model.Alarm
import pokitmons.pokit.core.ui.theme.PokitTheme

@Preview(showBackground = true)
@Composable
internal fun Preview() {
    PokitTheme {
        Column {
            AlarmScreen(
                alarms = listOf(Alarm(id = "1", title = "title1", thumbnail = ""), Alarm(id = "2", title = "title2", thumbnail = ""))
            )
        }
    }
}
