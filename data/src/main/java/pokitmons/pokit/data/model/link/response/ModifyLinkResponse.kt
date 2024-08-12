package pokitmons.pokit.data.model.link.response

import kotlinx.serialization.Serializable

@Serializable
data class ModifyLinkResponse(
    val contentId: Int = 0,
    val categoryId: Int = 0,
    val data: String = "",
    val title: String = "",
    val memo: String = "",
    val alertYn: String = "",
    val createdAt: String = "",
    val favorites: Boolean = true,
)

