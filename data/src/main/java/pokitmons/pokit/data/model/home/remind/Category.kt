package pokitmons.pokit.data.model.home.remind

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val categoryId: Int,
    val categoryName: String,
)
