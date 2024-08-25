package pokitmons.pokit.domain.usecase.pokit

import pokitmons.pokit.domain.commom.PokitError
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.pokit.Pokit
import pokitmons.pokit.domain.repository.pokit.PokitRepository
import javax.inject.Inject

class GetUncategorizedPokitUseCase @Inject constructor(
    private val repository: PokitRepository,
) {
    companion object {
        const val UNCATEGORIZED_POKIT_NAME = "미분류"
    }

    suspend fun getUncategoriezdPokit(): PokitResult<Pokit> {
        val response = repository.getPokits(filterUncategorized = false, size = 30, page = 0)
        if (response is PokitResult.Success) {
            val uncategorizedPokit = response.result.find { it.name == UNCATEGORIZED_POKIT_NAME }
            return if (uncategorizedPokit != null) {
                PokitResult.Success(uncategorizedPokit)
            } else {
                PokitResult.Error(error = PokitError("미분류 카테고리를 찾을 수 없습니다.", "C_9999"))
            }
        } else {
            response as PokitResult.Error
            return PokitResult.Error(error = response.error.copy())
        }
    }
}
