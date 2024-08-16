package pokitmons.pokit.data.api

import pokitmons.pokit.data.model.auth.request.SNSLoginRequest
import pokitmons.pokit.data.model.auth.request.SignUpRequest
import pokitmons.pokit.data.model.auth.response.DuplicateNicknameResponse
import pokitmons.pokit.data.model.auth.response.SNSLoginResponse
import pokitmons.pokit.data.model.auth.response.SignUpResponse
import pokitmons.pokit.data.model.home.remind.Remind
import pokitmons.pokit.data.model.home.remind.RemindResponse
import pokitmons.pokit.domain.model.pokit.PokitsSort
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RemindApi {
    @GET("remind/unread")
    suspend fun getUnreadContents(
        @Query("size") size: Int = 10,
        @Query("page") page: Int = 0,
        @Query("sort") sort: String = PokitsSort.RECENT.value,
    ): RemindResponse

    @GET("remind/today")
    suspend fun getTodayContents(
        @Query("size") size: Int = 10,
        @Query("page") page: Int = 0,
        @Query("sort") sort: String = PokitsSort.RECENT.value,
    ): List<Remind>

    @GET("remind/bookmark")
    suspend fun getBookmarkContents(
        @Query("size") size: Int = 10,
        @Query("page") page: Int = 0,
        @Query("sort") sort: String = PokitsSort.RECENT.value,
    ): RemindResponse
}
