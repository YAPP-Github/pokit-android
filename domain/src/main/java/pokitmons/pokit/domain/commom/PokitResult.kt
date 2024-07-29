package pokitmons.pokit.domain.commom

sealed interface PokitResult<out T> {
    data class Success<T>(val result: T) : PokitResult<T>
    data class Error(val error: PokitError) : PokitResult<Nothing>
}
