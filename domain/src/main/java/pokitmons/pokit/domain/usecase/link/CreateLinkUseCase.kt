package pokitmons.pokit.domain.usecase.link

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.repository.link.LinkRepository
import javax.inject.Inject

class CreateLinkUseCase @Inject constructor(
    private val repository: LinkRepository,
) {
    suspend fun createLink(
        data: String,
        title: String,
        categoryId: Int,
        memo: String,
        alertYn: String,
        thumbNail: String,
    ): PokitResult<Int> {
        return repository.createLink(
            data = data,
            title = title,
            categoryId = categoryId,
            memo = memo,
            alertYn = alertYn,
            thumbNail = thumbNail
        )
    }
}
