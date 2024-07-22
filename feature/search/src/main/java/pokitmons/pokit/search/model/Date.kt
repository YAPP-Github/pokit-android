package pokitmons.pokit.search.model

data class Date(
    val year: Int = 2024,
    val month: Int = 1,
    val day: Int = 1,
) {
    override fun toString(): String {
        return "${year % 1000}.$month.$day"
    }
}
