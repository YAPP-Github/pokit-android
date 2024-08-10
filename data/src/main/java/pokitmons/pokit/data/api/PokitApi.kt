package pokitmons.pokit.data.api

import pokitmons.pokit.data.model.pokit.request.CreatePokitRequest
import pokitmons.pokit.data.model.pokit.request.ModifyPokitRequest
import pokitmons.pokit.data.model.pokit.response.CreatePokitResponse
import pokitmons.pokit.data.model.pokit.response.GetPokitCountResponse
import pokitmons.pokit.data.model.pokit.response.GetPokitImagesResponseItem
import pokitmons.pokit.data.model.pokit.response.GetPokitResponse
import pokitmons.pokit.data.model.pokit.response.GetPokitsResponse
import pokitmons.pokit.data.model.pokit.response.ModifyPokitResponse
import pokitmons.pokit.domain.model.pokit.PokitsSort
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PokitApi {
    @GET("category")
    suspend fun getPokits(
        @Query("filterUncategorized") filterUncategorized: Boolean = true,
        @Query("size") size: Int = 10,
        @Query("page") page: Int = 0,
        @Query("sort") sort: String = PokitsSort.RECENT.value,
    ) : GetPokitsResponse

    @POST("category")
    suspend fun createPokit(
        @Body createPokitRequest: CreatePokitRequest
    ) : CreatePokitResponse

    @PATCH("category/{categoryId}")
    suspend fun modifyPokit(
        @Path("categoryId") categoryId : Int,
        @Body modifyPokitRequest: ModifyPokitRequest
    ) : ModifyPokitResponse

    @GET("category/images")
    suspend fun getPokitImages(): List<GetPokitImagesResponseItem>

    @GET("category/{categoryId}")
    suspend fun getPokit(
        @Path("categoryId") categoryId: Int
    ): GetPokitResponse

    @PUT("category/{categoryId}")
    suspend fun deletePokit(
        @Path("categoryId") categoryId: Int
    )

    @GET("category/count")
    suspend fun getPokitCount() : GetPokitCountResponse
}
