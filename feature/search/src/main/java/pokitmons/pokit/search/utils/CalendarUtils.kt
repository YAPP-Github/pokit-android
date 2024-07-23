package pokitmons.pokit.search.utils

import pokitmons.pokit.search.R
import pokitmons.pokit.search.model.CalendarCell
import pokitmons.pokit.search.model.Date
import java.util.Calendar

fun getCells(
    year: Int,
    month: Int,
    startDate: Date?,
    endDate: Date?,
) : List<CalendarCell> {
    val cells = getDefaultCalendarCells(year = year, month = month)
    if (startDate == null && endDate == null) {
        return cells
    }

    if (startDate != null && endDate != null) {
        val changedCells = cells.map { cell ->
            cell.copy(selected = (cell.date >= startDate && cell.date <= endDate))
        }
        return changedCells
    }

    val selectDate = startDate ?: endDate ?: return cells
    val changedCells = cells.map { cell ->
        cell.copy(selected = (cell.date == selectDate))
    }
    return changedCells
}


private fun getDayAmountOfMonth(year: Int, month: Int): Int {
    return when (month) {
        2 -> {
            if (year % 4 != 0 || (year % 100 == 0 && year % 400 != 0)) {
                28
            } else {
                29
            }
        }

        in listOf(1, 3, 5, 7, 8, 10, 12) -> {
            31
        }

        else -> {
            30
        }
    }
}


/**
 * 달력 상에서 이전달에 해당하는 요일의 리스트를 리턴합니다.
 */
private fun lastDaysOfPrevMonth(year: Int, month: Int): List<Int> {
    val calendar = Calendar.getInstance()
    val prevMonthIndex = month - 1

    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.MONTH, prevMonthIndex)
    calendar.set(Calendar.DAY_OF_MONTH, 1)

    val startDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    val amountOfDayOfPrevMonth = getDayAmountOfMonth(year, month - 1)

    return List(startDayOfWeek - 1) { i -> amountOfDayOfPrevMonth - (startDayOfWeek - 2 - i) }
}

/**
 * 달력 상에서 다음달에 해당하는 요일의 리스트를 리턴합니다.
 */
private fun firstDaysOfNextMonth(year: Int, month: Int): List<Int> {
    val calendar = Calendar.getInstance()

    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.MONTH, month)
    calendar.set(Calendar.DAY_OF_MONTH, 1)

    val startDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

    return List(8 - startDayOfWeek) { it + 1 }
}

private fun getDefaultCalendarCells(year : Int, month: Int) : List<CalendarCell> {
    val lastMonthCells = lastDaysOfPrevMonth(year, month).map { day ->
        if (month == 1) {
            CalendarCell(date = Date(year = year - 1, month = 12, day = day))
        } else {
            CalendarCell(date = Date(year = year, month = month - 1, day = day))
        }
    }

    val datOfMonth = getDayAmountOfMonth(year, month)
    val currentMonthCells = (1..datOfMonth).map { day ->
        CalendarCell(date = Date(year = year, month = month, day = day))
    }

    val nextMonthCells = firstDaysOfNextMonth(year, month).map { day ->
        if (month == 12) {
            CalendarCell(date = Date(year = year + 1, month = 1, day = day))
        } else {
            CalendarCell(date = Date(year = year, month = month + 1, day = day))
        }
    }

    return lastMonthCells + currentMonthCells + nextMonthCells
}

val weekDayStringResources = listOf(R.string.SUN, R.string.MON, R.string.TUE, R.string.WED, R.string.THU, R.string.FRI, R.string.SAT)
