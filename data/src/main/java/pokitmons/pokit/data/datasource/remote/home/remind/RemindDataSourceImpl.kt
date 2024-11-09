package pokitmons.pokit.data.datasource.remote.home.remind

import pokitmons.pokit.data.api.RemindApi
import pokitmons.pokit.data.model.home.remind.BookmarkContentCountResponse
import pokitmons.pokit.data.model.home.remind.Remind
import pokitmons.pokit.data.model.home.remind.RemindRequest
import pokitmons.pokit.data.model.home.remind.RemindResponse
import pokitmons.pokit.data.model.home.remind.UnreadContentCountResponse
import javax.inject.Inject

class RemindDataSourceImpl @Inject constructor(private val remindApi: RemindApi) : RemindDataSource {
    override suspend fun getUnreadContents(remindRequest: RemindRequest): RemindResponse {
        return remindApi.getUnreadContents()
    }

    override suspend fun getUnreadContentsCount(): UnreadContentCountResponse {
        return remindApi.getUnreadContentsCount()
    }

    override suspend fun getTodayContents(remindRequest: RemindRequest): List<Remind> {
        return remindApi.getTodayContents()
    }

    override suspend fun getBookmarkContents(remindRequest: RemindRequest): RemindResponse {
        return remindApi.getBookmarkContents()
    }

    override suspend fun getBookmarkContentsCount(): BookmarkContentCountResponse {
        return remindApi.getBookmarkContentsCount()
    }
}
