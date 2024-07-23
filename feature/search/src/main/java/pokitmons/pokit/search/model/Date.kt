package pokitmons.pokit.search.model

data class Date(
    val year: Int = 2024,
    val month: Int = 1,
    val day: Int = 1,
) : Comparable<Date> {
    override fun compareTo(other: Date): Int {
        return compareValuesBy(this, other, {it.year}, {it.month}, {it.day})
    }

    override fun toString(): String {
        return "${year % 1000}.$month.$day"
    }
}
