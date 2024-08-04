package pokitmons.pokit.domain.model.pokit

enum class PokitsSort(val value: String) {
    RECENT("createdAt,desc"), ALPHABETICAL("name,asc")
}
