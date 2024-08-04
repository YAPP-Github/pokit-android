package pokitmons.pokit.domain.repository.pokit

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.pokit.Pokit
import pokitmons.pokit.domain.model.pokit.PokitsSort

interface PokitRepository {
    suspend fun getPokits(
        filterUncategorized : Boolean = true,
        size : Int = 10,
        page : Int = 0,
        sort: PokitsSort = PokitsSort.RECENT
    ) : PokitResult<List<Pokit>>
}
