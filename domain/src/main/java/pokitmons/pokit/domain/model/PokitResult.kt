package pokitmons.pokit.domain.model

sealed interface PokitResult<out T> {
    data class Success<T>(val result: T) : PokitResult<T>
    data class Error(val error: PokitError) : PokitResult<Nothing>
}
