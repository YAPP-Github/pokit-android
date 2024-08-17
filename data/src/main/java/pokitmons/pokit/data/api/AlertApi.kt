package pokitmons.pokit.data.api

import pokitmons.pokit.data.model.alert.GetAlertsResponse
import pokitmons.pokit.domain.model.link.LinksSort
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AlertApi {
    @GET("alert")
    suspend fun getAlerts(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: List<String> = listOf(LinksSort.RECENT.value),
    ): GetAlertsResponse

    @PUT("alert/{alertId}")
    suspend fun deleteAlert(
        @Path("alertId") alertId: Int,
    )
}
