package pokitmons.pokit.domain.model.pokit

data class Pokit(
    val categoryId: Int,
    val userId: Int,
    val name: String,
    val image: Image,
    val linkCount: Int,
) {
    data class Image(
        val id: Int,
        val url: String,
    )
}
