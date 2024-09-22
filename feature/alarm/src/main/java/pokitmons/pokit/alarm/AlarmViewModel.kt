package pokitmons.pokit.alarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pokitmons.pokit.alarm.model.Alarm
import pokitmons.pokit.core.feature.model.paging.PagingLoadResult
import pokitmons.pokit.core.feature.model.paging.PagingSource
import pokitmons.pokit.core.feature.model.paging.PagingState
import pokitmons.pokit.core.feature.model.paging.SimplePaging
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.usecase.alert.DeleteAlertUseCase
import pokitmons.pokit.domain.usecase.alert.GetAlertsUseCase
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val getAlertsUseCase: GetAlertsUseCase,
    private val deleteAlertUseCase: DeleteAlertUseCase,
) : ViewModel() {

    private val alarmPagingSource = object : PagingSource<Alarm> {
        override suspend fun load(pageIndex: Int, pageSize: Int): PagingLoadResult<Alarm> {
            val response = getAlertsUseCase.getAlerts(page = pageIndex, size = pageSize)
            return PagingLoadResult.fromPokitResult(
                pokitResult = response,
                mapper = { alerts -> alerts.map { Alarm.fromDomainAlarm(it) } }
            )
        }
    }

    private val alarmPaging = SimplePaging(
        pagingSource = alarmPagingSource,
        getKeyFromItem = { alarm -> alarm.id },
        coroutineScope = viewModelScope
    )

    val alarms: StateFlow<List<Alarm>> = alarmPaging.pagingData
    val alarmsState: StateFlow<PagingState> = alarmPaging.pagingState

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
                    alarmPaging.deleteItem(alarmId)
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
            alarmPaging.modifyItem(targetItem = targetAlarm.copy(read = true))
        }
    }
}
