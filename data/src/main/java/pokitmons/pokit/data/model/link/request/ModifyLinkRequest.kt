package pokitmons.pokit.data.model.link.request

import kotlinx.serialization.Serializable

@Serializable
data class ModifyLinkRequest(
    val data: String,
    val title: String,
    val categoryId: Int,
    val memo: String,
    val alertYn: String,
    val thumbNail: String?,
)
