package pokitmons.pokit.data.api

import pokitmons.pokit.data.model.link.response.GetLinksResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LinkApi {
    @GET("content/{categoryId}")
    suspend fun getLinks(
        @Path("categoryId") categoryId: Int = 0,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10,
        @Query("sort") sort: List<String> = emptyList(),
    ): GetLinksResponse
}
