package pokitmons.pokit.search.model

import androidx.compose.runtime.Immutable
import pokitmons.pokit.search.R

@Immutable
data class Filter(
    val selectedPokits: List<Pokit> = emptyList(),
    val bookmark: Boolean = true,
    val notRead: Boolean = true,
    val startDate: Date? = null,
    val endDate: Date? = null
) {
    fun addPokit(pokit: Pokit) : Filter {
        return if (selectedPokits.contains(pokit)) {
            this
        } else {
            this.copy(selectedPokits = listOf(pokit) + selectedPokits)
        }
    }

    fun getDateString() : String? {
        return if (startDate != null && endDate != null) {
            "${startDate}~${endDate}"
        } else if (startDate != null) {
            "$startDate"
        } else {
            null
        }
    }
}

enum class FilterType(val stringResourceId : Int, val index : Int) {
    Pokit(R.string.pokit, 0), Collect(R.string.collect_show, 1), Period(R.string.period, 2)
}
