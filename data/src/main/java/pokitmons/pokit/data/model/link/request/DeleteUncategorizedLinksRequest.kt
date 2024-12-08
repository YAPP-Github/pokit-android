package pokitmons.pokit.data.model.link.request

import kotlinx.serialization.Serializable

@Serializable
data class DeleteUncategorizedLinksRequest(
    val contentId: List<Int>
)
