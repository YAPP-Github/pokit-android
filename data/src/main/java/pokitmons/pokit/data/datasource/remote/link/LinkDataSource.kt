package pokitmons.pokit.data.datasource.remote.link

import pokitmons.pokit.data.model.link.response.GetLinksResponse

interface LinkDataSource {
    suspend fun getLinks(categoryId : Int, page: Int, size: Int, sort: List<String>): GetLinksResponse
}
