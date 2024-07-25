package pokitmons.pokit.search.components.filterbottomsheet

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.components.atom.button.PokitButton
import pokitmons.pokit.core.ui.components.atom.button.attributes.PokitButtonSize
import pokitmons.pokit.core.ui.components.atom.checkbox.PokitCheckbox
import pokitmons.pokit.core.ui.components.atom.chip.PokitChip
import pokitmons.pokit.core.ui.components.atom.chip.attributes.PokitChipIconPosiion
import pokitmons.pokit.core.ui.components.atom.chip.attributes.PokitChipState
import pokitmons.pokit.core.ui.components.block.pokitlist.PokitList
import pokitmons.pokit.core.ui.components.block.tap.PokitTap
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.search.R
import pokitmons.pokit.search.components.calendar.CalendarView
import pokitmons.pokit.search.model.CalendarPage
import pokitmons.pokit.search.model.Filter
import pokitmons.pokit.search.model.FilterType
import pokitmons.pokit.search.model.Pokit
import pokitmons.pokit.search.model.samplePokits
import pokitmons.pokit.core.ui.R.string as coreString

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilterBottomSheetContent(
    filter: Filter = Filter(),
    firstShowType: FilterType = FilterType.Pokit,
    onSaveClilck: (Filter) -> Unit = {},
    pokits: List<Pokit> = samplePokits,
) {
    var currentFilter by remember { mutableStateOf(filter) }
    var currentShowType by remember { mutableStateOf(firstShowType) }
    val scrollState = rememberScrollState()
    val pagerState = rememberPagerState(
        initialPage = firstShowType.index
    ) {
        FilterType.entries.size
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            FilterType.entries.map { entry ->
                PokitTap(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = entry.stringResourceId),
                    data = entry,
                    onClick = remember {
                        { type ->
                            currentShowType = type
                        }
                    },
                    selectedItem = currentShowType
                )
            }
        }

        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .height(390.dp),
            state = pagerState,
            userScrollEnabled = false
        ) {
            when (currentShowType) {
                FilterType.Pokit -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(pokits) { pokit ->
                            PokitList(
                                item = pokit,
                                title = pokit.title,
                                sub = stringResource(id = coreString.pokit_count_format, pokit.count),
                                onClickItem = { item ->
                                    currentFilter = currentFilter.addPokit(item)
                                }
                            )
                        }
                    }
                }

                FilterType.Collect -> {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() },
                                ) {
                                    currentFilter = currentFilter.copy(bookmark = !currentFilter.bookmark)
                                }
                                .padding(12.dp)
                        ) {
                            PokitCheckbox(
                                checked = currentFilter.bookmark,
                                onClick = {currentFilter = currentFilter.copy(bookmark = !currentFilter.bookmark)}
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = stringResource(id = R.string.bookmark),
                                style = PokitTheme.typography.body1Medium.copy(color = PokitTheme.colors.textPrimary)
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    currentFilter = currentFilter.copy(notRead = !currentFilter.notRead)
                                }
                                .padding(12.dp)
                        ) {
                            PokitCheckbox(
                                checked = currentFilter.notRead,
                                onClick = {
                                    currentFilter = currentFilter.copy(notRead = !currentFilter.notRead)
                                }
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = stringResource(id = R.string.not_read),
                                style = PokitTheme.typography.body1Medium.copy(color = PokitTheme.colors.textPrimary)
                            )
                        }
                    }
                }

                FilterType.Period -> {
                    CalendarView(
                        calendarPage = CalendarPage(filter.endDate),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        startDate = currentFilter.startDate,
                        endDate = currentFilter.endDate,
                        onClickCell = remember {
                            { date ->
                                val startDate = currentFilter.startDate
                                val endDate = currentFilter.endDate
                                currentFilter = if (startDate != null && endDate == null) {
                                    if (date <= startDate) {
                                        currentFilter.copy(startDate = date)
                                    } else {
                                        currentFilter.copy(endDate = date)
                                    }
                                } else {
                                    currentFilter.copy(
                                        startDate = date,
                                        endDate = null
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = PokitTheme.colors.borderTertiary
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .horizontalScroll(scrollState),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Spacer(modifier = Modifier.width(20.dp).height(32.dp))

            currentFilter.selectedPokits.map { pokit ->
                PokitChip(
                    data = pokit,
                    text = pokit.title,
                    removeIconPosition = PokitChipIconPosiion.RIGHT,
                    state = PokitChipState.STROKE,
                    onClickRemove = remember {
                        { item ->
                            currentFilter = currentFilter.copy(
                                selectedPokits = currentFilter.selectedPokits.filter { it.id != item.id }
                            )
                        }
                    },
                    onClickItem = null
                )
            }

            if (currentFilter.bookmark) {
                PokitChip(
                    data = stringResource(id = R.string.bookmark),
                    text = stringResource(id = R.string.bookmark),
                    removeIconPosition = PokitChipIconPosiion.RIGHT,
                    state = PokitChipState.STROKE,
                    onClickRemove = remember {
                        {
                            currentFilter = currentFilter.copy(bookmark = false)
                        }
                    },
                    onClickItem = null
                )
            }

            if (currentFilter.notRead) {
                PokitChip(
                    data = stringResource(id = R.string.not_read),
                    text = stringResource(id = R.string.not_read),
                    removeIconPosition = PokitChipIconPosiion.RIGHT,
                    state = PokitChipState.STROKE,
                    onClickRemove = remember {
                        {
                            currentFilter = currentFilter.copy(notRead = false)
                        }
                    },
                    onClickItem = null
                )
            }

            currentFilter.getDateString()?.let { dateString ->
                PokitChip(
                    data = dateString,
                    text = dateString,
                    removeIconPosition = PokitChipIconPosiion.RIGHT,
                    state = PokitChipState.STROKE,
                    onClickRemove = remember {
                        {
                            currentFilter = currentFilter.copy(startDate = null, endDate = null)
                        }
                    },
                    onClickItem = null
                )
            }

            Spacer(modifier = Modifier.width(20.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            PokitButton(
                modifier = Modifier.fillMaxWidth(),
                size = PokitButtonSize.LARGE,
                text = stringResource(id = R.string.search),
                icon = null,
                onClick = remember {
                    {
                        onSaveClilck(currentFilter)
                    }
                }
            )
        }
    }
}
