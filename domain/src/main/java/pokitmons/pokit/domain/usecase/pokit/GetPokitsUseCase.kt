package pokitmons.pokit.domain.usecase.pokit

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.pokit.Pokit
import pokitmons.pokit.domain.model.pokit.PokitsSort
import pokitmons.pokit.domain.repository.pokit.PokitRepository
import javax.inject.Inject

class GetPokitsUseCase @Inject constructor(
    private val repository: PokitRepository,
) {
    suspend fun getPokits(
        filterUncategorized: Boolean = true,
        size: Int = 10,
        page: Int = 0,
        sort: PokitsSort = PokitsSort.RECENT,
    ): PokitResult<List<Pokit>> {
        return repository.getPokits(
            filterUncategorized = filterUncategorized,
            size = size,
            page = page,
            sort = sort
        )
    }
}
