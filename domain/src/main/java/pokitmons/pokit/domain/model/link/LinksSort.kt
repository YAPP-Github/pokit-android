package pokitmons.pokit.domain.model.link

enum class LinksSort(val value: String) {
    RECENT("createdAt,desc"), OLDER("createdAt,asc")
}
