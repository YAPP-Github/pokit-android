package pokitmons.pokit.domain.usecase.link

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.repository.link.LinkRepository
import javax.inject.Inject

class ModifyPokitOfLinksUseCase @Inject constructor(
    private val repository: LinkRepository,
) {
    suspend fun modifyPokit(linkIds: List<Int>, categoryId: Int): PokitResult<Unit> {
        return repository.modifyPokitOfLinks(linkIds = linkIds, categoryId = categoryId)
    }
}
