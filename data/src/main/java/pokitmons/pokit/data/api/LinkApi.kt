package pokitmons.pokit.data.api

import pokitmons.pokit.data.model.link.request.ModifyLinkRequest
import pokitmons.pokit.data.model.link.response.ApplyBookmarkResponse
import pokitmons.pokit.data.model.link.response.GetLinkResponse
import pokitmons.pokit.data.model.link.response.GetLinksResponse
import pokitmons.pokit.data.model.link.response.ModifyLinkResponse
import pokitmons.pokit.domain.model.link.LinksSort
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface LinkApi {
    @GET("content/{categoryId}")
    suspend fun getLinks(
        @Path("categoryId") categoryId: Int = 0,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10,
        @Query("sort") sort: List<String> = listOf(LinksSort.RECENT.value),
        @Query("isRead") isRead: Boolean? = null,
        @Query("favorites") favorites: Boolean? = null,
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null,
        @Query("categoryIds") categoryIds: List<Int>? = null,
    ): GetLinksResponse

    @GET("content")
    suspend fun searchLinks(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10,
        @Query("sort") sort: List<String> = listOf(LinksSort.RECENT.value),
        @Query("isRead") isRead: Boolean? = null,
        @Query("favorites") favorites: Boolean? = null,
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null,
        @Query("categoryIds") categoryIds: List<Int>? = null,
        @Query("searchWord") searchWord: String = "",
    ): GetLinksResponse

    @PUT("content/{contentId}")
    suspend fun deleteLink(
        @Path("contentId") contentId: Int = 0,
    ): Response<Unit>

    @POST("content/{contentId}")
    suspend fun getLink(
        @Path("contentId") contentId: Int = 0,
    ): GetLinkResponse

    @PATCH("content/{contentId}")
    suspend fun modifyLink(
        @Path("contentId") contentId: Int,
        @Body modifyLinkRequest: ModifyLinkRequest,
    ): ModifyLinkResponse

    @POST("content")
    suspend fun createLink(
        @Body createLinkRequest: ModifyLinkRequest,
    ): ModifyLinkResponse

    @PUT("content/{contentId}/bookmark")
    suspend fun cancelBookmark(@Path("contentId") contentId: Int): Response<Unit>

    @POST("content/{contentId}/bookmark")
    suspend fun applyBookmark(@Path("contentId") contentId: Int): ApplyBookmarkResponse

    @GET("content/uncategorized")
    suspend fun getUncategorizedLinks(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10,
        @Query("sort") sort: List<String> = listOf(LinksSort.RECENT.value),
    ): GetLinksResponse
}
