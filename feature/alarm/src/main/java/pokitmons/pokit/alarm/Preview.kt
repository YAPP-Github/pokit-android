package pokitmons.pokit.alarm

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import pokitmons.pokit.alarm.model.Alarm
import pokitmons.pokit.alarm.model.AlarmScreenSideEffect
import pokitmons.pokit.core.feature.flow.EventFlow
import pokitmons.pokit.core.feature.flow.MutableEventFlow
import pokitmons.pokit.core.feature.model.paging.PagingState
import pokitmons.pokit.core.ui.theme.PokitTheme

@Preview(showBackground = true)
@Composable
internal fun Preview() {
    PokitTheme {
        Column {
            AlarmScreen(
                onClickBack = {},
                viewModel = dummyAlarmViewModel
            )
        }
    }
}

private val dummyAlarmViewModel = object : AlarmViewModelInterface {
    override val sideEffect: EventFlow<AlarmScreenSideEffect>
        get() = MutableEventFlow()
    override val alarms: StateFlow<List<Alarm>>
        get() = MutableStateFlow(listOf(Alarm(id = "1", title = "title1", thumbnail = ""), Alarm(id = "2", title = "title2", thumbnail = "")))
    override val alarmsState: StateFlow<PagingState>
        get() = MutableStateFlow(PagingState.IDLE)

    override fun loadNextAlarms() {}

    override fun refreshAlarms() {}

    override fun removeAlarm(alarmId: String) {}

    override fun readAlarmThenMoveToModifyLink(alarmId: String) {}
}
