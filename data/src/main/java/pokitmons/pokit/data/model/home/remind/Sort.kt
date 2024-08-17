package pokitmons.pokit.data.model.home.remind

import kotlinx.serialization.Serializable

@Serializable
data class Sort(
    val ascending: Boolean,
    val direction: String,
    val ignoreCase: Boolean,
    val nullHandling: String,
    val property: String,
)
