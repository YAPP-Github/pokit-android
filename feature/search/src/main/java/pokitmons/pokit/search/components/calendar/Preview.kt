package pokitmons.pokit.search.components.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pokitmons.pokit.core.ui.theme.PokitTheme
import pokitmons.pokit.search.model.CalendarPage
import pokitmons.pokit.search.model.Date

@Preview(showBackground = true)
@Composable
private fun Preview() {
    var startDate by remember { mutableStateOf<Date?>(null) }
    var endDate by remember { mutableStateOf<Date?>(null) }

    PokitTheme {
        Column(
            modifier = Modifier.width(300.dp).fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CalendarView(
                calendarPage = CalendarPage(endDate),
                startDate = startDate,
                endDate = endDate,
                onClickCell = remember {
                    { date ->
                        val currentStartDate = startDate
                        if (currentStartDate != null && endDate == null) {
                            if (date <= currentStartDate) {
                                startDate = date
                            } else {
                                endDate = date
                            }
                        } else {
                            startDate = date
                            endDate = null
                        }
                    } 
                }
            )
        }
    }
}
