package pokitmons.pokit.data.api

import pokitmons.pokit.data.model.pokit.response.GetPokitsResponse
import pokitmons.pokit.domain.model.pokit.PokitsSort
import retrofit2.http.GET
import retrofit2.http.Query

interface PokitApi {
    @GET("category")
    suspend fun getPokits(
        @Query("filterUncategorized") filterUncategorized: Boolean = true,
        @Query("size") size: Int = 10,
        @Query("page") page: Int = 0,
        @Query("sort") sort: String = PokitsSort.RECENT.value,
    ) : GetPokitsResponse
}
