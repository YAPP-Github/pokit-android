package pokitmons.pokit.domain.repository.pokit

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.pokit.Pokit
import pokitmons.pokit.domain.model.pokit.PokitsSort

interface PokitRepository {
    suspend fun getPokits(
        filterUncategorized: Boolean = true,
        size: Int = 10,
        page: Int = 0,
        sort: PokitsSort = PokitsSort.RECENT,
    ): PokitResult<List<Pokit>>

    suspend fun createPokit(
        name: String,
        imageId: Int,
    ): PokitResult<Int>

    suspend fun modifyPokit(
        pokitId: Int,
        name: String,
        imageId: Int,
    ): PokitResult<Int>

    suspend fun getPokitImages(): PokitResult<List<Pokit.Image>>

    suspend fun getPokit(pokitId: Int): PokitResult<Pokit>

    suspend fun deletePokit(pokitId: Int): PokitResult<Unit>

    suspend fun getPokitCount(): PokitResult<Int>
}
