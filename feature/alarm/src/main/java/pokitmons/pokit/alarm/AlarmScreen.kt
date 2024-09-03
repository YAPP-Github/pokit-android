package pokitmons.pokit.alarm

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import pokitmons.pokit.alarm.components.alarmitem.AlarmItem
import pokitmons.pokit.alarm.components.toolbar.Toolbar
import pokitmons.pokit.alarm.model.Alarm
import pokitmons.pokit.alarm.paging.SimplePagingState
import pokitmons.pokit.core.ui.components.atom.loading.LoadingProgress
import pokitmons.pokit.core.ui.components.template.pokki.Pokki
import pokitmons.pokit.core.ui.components.template.pokkierror.ErrorPokki
import pokitmons.pokit.core.ui.R.string as coreString

@Composable
fun AlarmScreenContainer(
    viewModel: AlarmViewModel,
    onNavigateToLinkModify: (String) -> Unit = {},
    onBackPressed: () -> Unit,
) {
    val alarms by viewModel.alarms.collectAsState()
    val alarmsState by viewModel.alarmsState.collectAsState()

    AlarmScreen(
        onClickBack = onBackPressed,
        onClickAlarm = remember {
            { alarmId ->
                viewModel.readAlarm(alarmId)
                onNavigateToLinkModify(alarmId)
            }
        },
        onClickAlarmRemove = viewModel::removeAlarm,
        alarms = alarms,
        alarmsState = alarmsState,
        loadNextAlarms = viewModel::loadNextAlarms,
        refreshAlarms = viewModel::refreshAlarms
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlarmScreen(
    onClickBack: () -> Unit = {},
    onClickAlarm: (String) -> Unit = {},
    onClickAlarmRemove: (String) -> Unit = {},
    alarms: List<Alarm> = emptyList(),
    alarmsState: SimplePagingState = SimplePagingState.IDLE,
    loadNextAlarms: () -> Unit = {},
    refreshAlarms: () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Toolbar(
            onClickBack = onClickBack,
            title = stringResource(id = R.string.alarm_box)
        )

        val alarmLazyColumnListState = rememberLazyListState()
        val startAlarmPaging = remember {
            derivedStateOf {
                alarmLazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.let { last ->
                    last.index >= alarmLazyColumnListState.layoutInfo.totalItemsCount - 3
                } ?: false
            }
        }

        LaunchedEffect(startAlarmPaging.value) {
            if (startAlarmPaging.value && alarmsState == SimplePagingState.IDLE) {
                loadNextAlarms()
            }
        }

        when {
            alarmsState == SimplePagingState.LOADING_INIT -> {
                LoadingProgress(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
            alarmsState == SimplePagingState.FAILURE_INIT -> {
                ErrorPokki(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    title = stringResource(id = coreString.title_error),
                    sub = stringResource(id = coreString.sub_error),
                    onClickRetry = refreshAlarms
                )
            }
            alarms.isEmpty() -> {
                Pokki(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    title = stringResource(id = coreString.title_empty_alarms),
                    sub = stringResource(id = coreString.sub_empty_alarms)
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    state = alarmLazyColumnListState
                ) {
                    items(
                        items = alarms,
                        key = { alarm -> alarm.id }
                    ) { alarm ->
                        AlarmItem(
                            modifier = Modifier.animateItemPlacement(),
                            alarm = alarm,
                            onClickAlarm = onClickAlarm,
                            onClickRemove = onClickAlarmRemove
                        )
                    }
                }
            }
        }

    }
}
