package pokitmons.pokit.alarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pokitmons.pokit.alarm.model.Alarm
import pokitmons.pokit.alarm.model.AlarmScreenSideEffect
import pokitmons.pokit.core.feature.flow.EventFlow
import pokitmons.pokit.core.feature.flow.MutableEventFlow
import pokitmons.pokit.core.feature.flow.asEventFlow
import pokitmons.pokit.core.feature.model.paging.PagingLoadResult
import pokitmons.pokit.core.feature.model.paging.PagingSource
import pokitmons.pokit.core.feature.model.paging.PagingState
import pokitmons.pokit.core.feature.model.paging.SimplePaging
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.usecase.alert.DeleteAlertUseCase
import pokitmons.pokit.domain.usecase.alert.GetAlertsUseCase
import javax.inject.Inject

@HiltViewModel
class AlarmViewModelImpl @Inject constructor(
    private val getAlertsUseCase: GetAlertsUseCase,
    private val deleteAlertUseCase: DeleteAlertUseCase,
) : ViewModel(), AlarmViewModelInterface {

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

    private val _sideEffect = MutableEventFlow<AlarmScreenSideEffect>()
    override val sideEffect: EventFlow<AlarmScreenSideEffect>
        get() = _sideEffect.asEventFlow()

    override val alarms: StateFlow<List<Alarm>>
        get() = alarmPaging.pagingData
    override val alarmsState: StateFlow<PagingState>
        get() = alarmPaging.pagingState

    init {
        viewModelScope.launch {
            alarmPaging.refresh()
        }
    }

    override fun loadNextAlarms() {
        viewModelScope.launch {
            alarmPaging.load()
        }
    }

    override fun refreshAlarms() {
        viewModelScope.launch {
            alarmPaging.refresh()
        }
    }

    override fun removeAlarm(alarmId: String) {
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

    override fun readAlarmThenMoveToModifyLink(alarmId: String) {
        val targetAlarm = alarms.value.find { it.id == alarmId } ?: return

        viewModelScope.launch {
            alarmPaging.modifyItem(targetItem = targetAlarm.copy(read = true))
            _sideEffect.emit(AlarmScreenSideEffect.NavigateToLinkModify(linkId = targetAlarm.contentId))
        }
    }
}
