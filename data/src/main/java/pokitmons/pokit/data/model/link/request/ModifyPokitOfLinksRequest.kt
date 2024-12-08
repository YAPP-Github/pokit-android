package pokitmons.pokit.data.model.link.request

import kotlinx.serialization.Serializable

@Serializable
data class ModifyPokitOfLinksRequest(
    val contentIds: List<Int>,
    val categoryId: Int
)
