package pokitmons.pokit.search.model

import java.util.Calendar

data class CalendarPage(
    val year: Int,
    val month: Int,
) {
    constructor(date: Date?) : this(
        year = date?.year ?: Calendar.getInstance().get(Calendar.YEAR),
        month = date?.month ?: (Calendar.getInstance().get(Calendar.MONTH) + 1)
    )

    fun prevPage(): CalendarPage {
        return if (month == 1) {
            CalendarPage(
                year = year - 1,
                month = 12
            )
        } else {
            CalendarPage(
                year = year,
                month = month - 1
            )
        }
    }

    fun nextPage(): CalendarPage {
        return if (month == 12) {
            CalendarPage(
                year = year + 1,
                month = 1
            )
        } else {
            CalendarPage(
                year = year,
                month = month + 1
            )
        }
    }
}
