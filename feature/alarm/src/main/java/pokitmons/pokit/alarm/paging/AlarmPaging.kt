package pokitmons.pokit.alarm.paging

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pokitmons.pokit.alarm.model.Alarm
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.usecase.alert.GetAlertsUseCase
import kotlin.coroutines.cancellation.CancellationException

class AlarmPaging(
    private val getAlertsUseCase: GetAlertsUseCase,
    private val perPage: Int = 10,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO),
    private val initPage: Int = 0,
    private val firstRequestPage: Int = 3,
) : SimplePaging<Alarm> {
    private val _pagingData: MutableStateFlow<List<Alarm>> = MutableStateFlow(emptyList())
    override val pagingData: StateFlow<List<Alarm>> = _pagingData.asStateFlow()

    private val _pagingState: MutableStateFlow<SimplePagingState> = MutableStateFlow(SimplePagingState.IDLE)
    override val pagingState: StateFlow<SimplePagingState> = _pagingState.asStateFlow()

    private var currentPageIndex = initPage
    private var requestJob: Job? = null

    override suspend fun refresh() {
        requestJob?.cancel()

        _pagingData.update { emptyList() }
        _pagingState.update { SimplePagingState.LOADING_INIT }
        requestJob = coroutineScope.launch {
            try {
                currentPageIndex = initPage
                val response = getAlertsUseCase.getAlerts(size = perPage * firstRequestPage, page = currentPageIndex)
                when (response) {
                    is PokitResult.Success -> {
                        val alarms = response.result.map { domainAlarm ->
                            Alarm.fromDomainAlarm(domainAlarm)
                        }
                        applyResponse(alarms, firstRequestPage)
                    }

                    is PokitResult.Error -> {
                        _pagingState.update { SimplePagingState.FAILURE_INIT }
                    }
                }
            } catch (exception: Exception) {
                if (exception !is CancellationException) {
                    _pagingState.update { SimplePagingState.FAILURE_INIT }
                }
            }
        }
    }

    override suspend fun load() {
        if (pagingState.value != SimplePagingState.IDLE) return

        requestJob?.cancel()
        _pagingState.update { SimplePagingState.LOADING_NEXT }

        requestJob = coroutineScope.launch {
            try {
                val response = getAlertsUseCase.getAlerts(size = perPage, page = currentPageIndex)
                when (response) {
                    is PokitResult.Success -> {
                        val alarms = response.result.map { domainAlarm ->
                            Alarm.fromDomainAlarm(domainAlarm)
                        }
                        applyResponse(alarms)
                    }

                    is PokitResult.Error -> {
                        _pagingState.update { SimplePagingState.FAILURE_NEXT }
                    }
                }
            } catch (exception: Exception) {
                if (exception !is CancellationException) {
                    _pagingState.update { SimplePagingState.FAILURE_NEXT }
                }
            }
        }
    }

    private fun applyResponse(dataInResponse: List<Alarm>, multiple: Int = 1) {
        if (dataInResponse.size < perPage * multiple) {
            _pagingState.update { SimplePagingState.LAST }
        } else {
            _pagingState.update { SimplePagingState.IDLE }
        }
        _pagingData.update { _pagingData.value + dataInResponse }
        currentPageIndex += multiple
    }

    override fun clear() {
        requestJob?.cancel()
        _pagingData.update { emptyList() }
        _pagingState.update { SimplePagingState.IDLE }
    }

    override suspend fun deleteItem(item: Alarm) {
        val capturedDataList = _pagingData.value
        _pagingData.update { capturedDataList.filter { it.id != item.id } }
    }

    override suspend fun modifyItem(item: Alarm) {
        val capturedDataList = _pagingData.value
        val targetData = capturedDataList.find { it.id == item.id } ?: return

        _pagingData.update {
            capturedDataList.map { data ->
                if (targetData.id == data.id) {
                    item
                } else {
                    data
                }
            }
        }
    }
}
