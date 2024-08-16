package pokitmons.pokit.data.model.link.request

import kotlinx.serialization.Serializable

@Serializable
class ModifyLinkRequest(
    val data: String = "",
    val title: String = "",
    val categoryId: Int = 0,
    val memo: String = "",
    val alertYn: String = "",
    val thumbNail: String = "",
)
