package pokitmons.pokit.data.model.home.remind

import kotlinx.serialization.Serializable

@Serializable
data class RemindResponse(
    val data: List<Remind> = emptyList(),
    val hasNext: Boolean = false,
    val page: Int = 0,
    val size: Int = 10,
    val sort: List<Sort> = emptyList()
)
