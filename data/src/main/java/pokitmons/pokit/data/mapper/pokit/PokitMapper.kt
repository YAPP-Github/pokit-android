package pokitmons.pokit.data.mapper.pokit

import pokitmons.pokit.data.model.pokit.response.GetPokitsResponse
import pokitmons.pokit.domain.model.pokit.Pokit

object PokitMapper {
    fun mapperToPokits(pokitsResponse: GetPokitsResponse) : List<Pokit> {
        return pokitsResponse.data.map { data ->
            Pokit(
                categoryId = data.categoryId,
                userId = data.userId,
                name = data.categoryName,
                image = Pokit.Image(
                    id = data.categoryImage.imageId,
                    url = data.categoryImage.imageUrl
                ),
                linkCount = data.contentCount
            )
        }
    }
}
