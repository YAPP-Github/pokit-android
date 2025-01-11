package pokitmons.pokit.data.datasource.remote.shared

import pokitmons.pokit.data.api.SharedApi
import pokitmons.pokit.data.model.link.response.GetLinksResponse
import javax.inject.Inject

class SharedDataSourceImpl @Inject constructor(private val sharedApi: SharedApi) : SharedDataSource {
    override suspend fun getSharedPokitContentsPreview(
        categoryId: Int,
        page: Int,
        size: Int,
        sort: List<String>
    ): GetLinksResponse {
        return sharedApi.getSharedPokitContentsPreview(
            categoryId = categoryId,
            page = page,
            size = size,
            sort = sort
        )
    }
}
