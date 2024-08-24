package pokitmons.pokit.data.model.alert

import kotlinx.serialization.Serializable

@Serializable
data class GetAlertsResponse(
    val data: List<Data> = emptyList(),
    val page: Int = 0,
    val size: Int = 10,
    val sort: List<Sort> = emptyList(),
    val hasNext: Boolean = true,
) {
    @Serializable
    data class Data(
        val id: Int,
        val userId: Int,
        val contentId: Int,
        val thumbNail: String,
        val title: String,
        val body: String,
        val createdAt: String,
    )

    @Serializable
    data class Sort(
        val direction: String,
        val nullHandling: String,
        val ascending: Boolean,
        val property: String,
        val ignoreCase: Boolean,
    )
}
