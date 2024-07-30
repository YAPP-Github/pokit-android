package pokitmons.pokit.data.model.common

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import pokitmons.pokit.domain.commom.PokitError
import pokitmons.pokit.domain.commom.PokitResult

fun <T> parseErrorResult(throwable: Throwable): PokitResult<T> {
    return try {
        val error: PokitErrorResponse = throwable.message?.let { errorBody ->
            Json.decodeFromString<PokitErrorResponse>(errorBody)
        } ?: PokitErrorResponse()
        val pokitError = PokitError(message = error.message, code = error.code)
        PokitResult.Error(pokitError)
    } catch (e: Exception) {
        PokitResult.Error(PokitError())
    }
}
