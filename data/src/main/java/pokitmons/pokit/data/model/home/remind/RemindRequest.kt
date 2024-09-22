package pokitmons.pokit.data.model.home.remind

import kotlinx.serialization.Serializable
import pokitmons.pokit.domain.model.pokit.PokitsSort

@Serializable
data class RemindRequest(
    val size: Int = 10,
    val page: Int = 0,
    val sort: PokitsSort = PokitsSort.RECENT,
)
