package pokitmons.pokit.core.feature.model.paging

import pokitmons.pokit.domain.commom.PokitResult

sealed interface PagingLoadResult<out T> {
    data class Success<T>(val result: List<T>) : PagingLoadResult<T>
    data class Error<T>(val errorCode: String) : PagingLoadResult<T>

    companion object {
        fun<T, K> fromPokitResult(pokitResult: PokitResult<K>, mapper: (K) -> List<T>): PagingLoadResult<T> {
            return if (pokitResult is PokitResult.Success) {
                Success(result = mapper(pokitResult.result))
            } else {
                Error(errorCode = (pokitResult as PokitResult.Error).error.code)
            }
        }
    }
}
