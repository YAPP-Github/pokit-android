package pokitmons.pokit.domain.usecase.link

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.repository.link.LinkRepository
import javax.inject.Inject

class ModifyLinkUseCase @Inject constructor(
    private val repository: LinkRepository,
) {
    suspend fun modifyLink(
        linkId: Int,
        data: String,
        title: String,
        categoryId: Int,
        memo: String,
        alertYn: String,
        thumbNail: String,
    ): PokitResult<Int> {
        return repository.modifyLink(
            linkId = linkId,
            data = data,
            title = title,
            categoryId = categoryId,
            memo = memo,
            alertYn = alertYn,
            thumbNail = thumbNail
        )
    }
}
