package pokitmons.pokit.search.model

data class CalendarCell(
    val date: Date = Date(),
    val selected: Boolean = false,
    val inSelectRange: Boolean = false,
    val currentMonth: Boolean = true
)
