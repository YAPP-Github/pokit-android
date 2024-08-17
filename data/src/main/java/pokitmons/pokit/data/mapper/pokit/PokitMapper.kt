package pokitmons.pokit.data.mapper.pokit

import pokitmons.pokit.data.model.pokit.response.GetPokitImagesResponseItem
import pokitmons.pokit.data.model.pokit.response.GetPokitResponse
import pokitmons.pokit.data.model.pokit.response.GetPokitsResponse
import pokitmons.pokit.domain.model.pokit.Pokit

object PokitMapper {
    private const val NOT_USE = 0

    fun mapperToPokits(pokitsResponse: GetPokitsResponse): List<Pokit> {
        return pokitsResponse.data.map { data ->
            Pokit(
                categoryId = data.categoryId,
                userId = data.userId,
                name = data.categoryName,
                image = Pokit.Image(
                    id = data.categoryImage.imageId,
                    url = data.categoryImage.imageUrl
                ),
                createdAt = data.createdAt,
                linkCount = data.contentCount
            )
        }
    }

    fun mapperToPokitImages(getPokitImagesResponse: List<GetPokitImagesResponseItem>): List<Pokit.Image> {
        return getPokitImagesResponse.map { image ->
            Pokit.Image(id = image.imageId, url = image.imageUrl)
        }
    }

    fun mapperToPokit(pokitResponse: GetPokitResponse): Pokit {
        return Pokit(
            categoryId = pokitResponse.categoryId,
            userId = NOT_USE,
            name = pokitResponse.categoryName,
            image = Pokit.Image(
                id = pokitResponse.categoryImage.imageId,
                url = pokitResponse.categoryImage.imageUrl
            ),
            linkCount = NOT_USE,
            createdAt = pokitResponse.createdAt
        )
    }
}
