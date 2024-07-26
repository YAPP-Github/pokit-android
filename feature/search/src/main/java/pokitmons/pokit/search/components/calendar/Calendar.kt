package pokitmons.pokit.search.components.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.search.R
import pokitmons.pokit.search.components.atom.CalendarCellState
import pokitmons.pokit.search.components.atom.CalendarCellView
import pokitmons.pokit.search.model.CalendarPage
import pokitmons.pokit.search.model.Date
import pokitmons.pokit.search.utils.getCells
import pokitmons.pokit.search.utils.weekDayStringResources

@Composable
internal fun CalendarView(
    calendarPage: CalendarPage,
    modifier: Modifier = Modifier,
    startDate: Date? = null,
    endDate: Date? = null,
    onClickCell: (Date) -> Unit = {},
) {
    var currentPage by remember { mutableStateOf(calendarPage) }
    val calendarCells = remember(
        currentPage,
        startDate,
        endDate
    ) {
        getCells(year = currentPage.year, month = currentPage.month, startDate = startDate, endDate = endDate)
    }

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.format_calendar, currentPage.year, currentPage.month),
                style = PokitTheme.typography.body1Bold.copy(color = PokitTheme.colors.textPrimary)
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                modifier = Modifier.size(40.dp),
                onClick = remember {
                    {
                        currentPage = currentPage.prevPage()
                    }
                }
            ) {
                Icon(
                    painter = painterResource(id = pokitmons.pokit.core.ui.R.drawable.icon_24_arrow_left),
                    contentDescription = "prev month"
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            IconButton(
                modifier = Modifier.size(40.dp),
                onClick = remember {
                    {
                        currentPage = currentPage.nextPage()
                    }
                }
            ) {
                Icon(
                    painter = painterResource(id = pokitmons.pokit.core.ui.R.drawable.icon_24_arrow_right),
                    contentDescription = "next month"
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(count = 7),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(weekDayStringResources) { stringRes ->
                Box(
                    modifier = Modifier.size(40.dp)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center,
                        text = stringResource(id = stringRes),
                        style = PokitTheme.typography.detail1.copy(color = PokitTheme.colors.textTertiary)
                    )
                }
            }

            items(calendarCells) { cell ->
                if (cell.date.year != currentPage.year || cell.date.month != currentPage.month) {
                    CalendarCellView(
                        date = cell.date,
                        onClick = null,
                        state = CalendarCellState.INACTIVE
                    )
                } else if (cell.selected) {
                    CalendarCellView(
                        date = cell.date,
                        onClick = onClickCell,
                        state = CalendarCellState.SELECTED
                    )
                } else if (cell.inSelectRange) {
                    CalendarCellView(
                        date = cell.date,
                        onClick = onClickCell,
                        state = CalendarCellState.IN_RANGE
                    )
                } else {
                    CalendarCellView(
                        date = cell.date,
                        onClick = onClickCell,
                        state = CalendarCellState.ACTIVE
                    )
                }
            }
        }
    }
}
