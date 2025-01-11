package pokitmons.pokit.data.api

import pokitmons.pokit.data.model.link.response.GetLinksResponse
import pokitmons.pokit.data.model.shared.GetSharedLinksResponse
import pokitmons.pokit.domain.model.link.LinksSort
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SharedApi {
    @GET("category/share/{categoryId}")
    suspend fun getSharedPokitContentsPreview(
        @Path("categoryId") categoryId: Int,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10,
        @Query("sort") sort: List<String> = listOf(LinksSort.RECENT.value)
    ): GetLinksResponse

    @POST("category/share/resign/{categoryId}/{resignUserId}")
    suspend fun sharePokit(
        @Path("categoryId") categoryId: Int,
        @Path("resignUserId") resignUserId: Int
    ): Response<Unit>

    @POST("category/share/accept/{categoryId}")
    suspend fun acceptPokit(
        @Path("categoryId") categoryId: Int
    ): Response<Unit>

    @POST("category/share/out/{categoryId}")
    suspend fun leavePokit(
        @Path("categoryId") categoryId: Int
    ): Response<Unit>

    @GET("category/share/callback")
    suspend fun callbackPokit(
        @Query("categoryId") categoryId: Int
    ): Response<Unit>
}
