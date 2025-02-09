package pokitmons.pokit.alarm

import kotlinx.coroutines.flow.StateFlow
import pokitmons.pokit.alarm.model.Alarm
import pokitmons.pokit.alarm.model.AlarmScreenSideEffect
import pokitmons.pokit.core.feature.flow.EventFlow
import pokitmons.pokit.core.feature.model.paging.PagingState

interface AlarmViewModelInterface {
    val sideEffect: EventFlow<AlarmScreenSideEffect>
    val alarms: StateFlow<List<Alarm>>
    val alarmsState: StateFlow<PagingState>

    fun loadNextAlarms()
    fun refreshAlarms()
    fun removeAlarm(alarmId: String)
    fun readAlarmThenMoveToModifyLink(alarmId: String)
}
