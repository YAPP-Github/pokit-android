package pokitmons.pokit.alarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pokitmons.pokit.alarm.model.Alarm
import pokitmons.pokit.alarm.paging.AlarmPaging
import pokitmons.pokit.alarm.paging.SimplePagingState
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.usecase.alert.DeleteAlertUseCase
import pokitmons.pokit.domain.usecase.alert.GetAlertsUseCase
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    getAlertsUseCase: GetAlertsUseCase,
    private val deleteAlertUseCase: DeleteAlertUseCase,
) : ViewModel() {

    private val alarmPaging = AlarmPaging(getAlertsUseCase = getAlertsUseCase)

    val alarms: StateFlow<List<Alarm>> = alarmPaging.pagingData
    val alarmsState: StateFlow<SimplePagingState> = alarmPaging.pagingState

    init {
        viewModelScope.launch {
            alarmPaging.refresh()
        }
    }

    fun removeAlarm(alarmId: String) {
        val id = alarmId.toIntOrNull() ?: return
        viewModelScope.launch {
            val response = deleteAlertUseCase.deleteAlert(id)
            if (response is PokitResult.Success) {
                viewModelScope.launch {
                    alarms.value.find { it.id == alarmId }?.let { targetItem ->
                        alarmPaging.deleteItem(targetItem)
                    }
                }
            }
        }
    }

    fun loadNextAlarms() {
        viewModelScope.launch {
            alarmPaging.load()
        }
    }

    fun refreshAlarms() {
        viewModelScope.launch {
            alarmPaging.refresh()
        }
    }

    fun readAlarm(alarmId: String) {
        val targetAlarm = alarms.value.find { it.id == alarmId } ?: return

        viewModelScope.launch {
            alarmPaging.modifyItem(item = targetAlarm.copy(read = true))
        }
    }
}
