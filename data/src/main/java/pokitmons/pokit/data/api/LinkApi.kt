package pokitmons.pokit.data.api

import pokitmons.pokit.data.model.link.response.GetLinksResponse
import pokitmons.pokit.domain.model.link.LinksSort
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LinkApi {
    @GET("content/{categoryId}")
    suspend fun getLinks(
        @Path("categoryId") categoryId: Int = 0,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10,
        @Query("sort") sort: List<String> = listOf(LinksSort.RECENT.value),
        @Query("isRead") isRead: Boolean = false,
        @Query("favorites") favorites: Boolean = false,
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null,
        @Query("categoryIds") categoryIds: List<Int>? = null,
    ): GetLinksResponse
}
