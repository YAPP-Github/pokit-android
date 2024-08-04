package pokitmons.pokit.data.model.pokit.request

import pokitmons.pokit.domain.model.pokit.PokitsSort

data class GetPokitsRequest(
    val filterUncategoriezd: Boolean = true,
    val size: Int = 10,
    val page: Int = 0,
    val sort: PokitsSort = PokitsSort.RECENT
)
