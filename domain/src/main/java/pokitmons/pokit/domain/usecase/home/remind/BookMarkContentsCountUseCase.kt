package pokitmons.pokit.domain.usecase.home.remind

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.repository.home.remind.RemindRepository
import javax.inject.Inject

class BookMarkContentsCountUseCase @Inject constructor(private val remindRepository: RemindRepository) {
    suspend fun getCount(): PokitResult<Int> {
        return remindRepository.getBookmarkContentsCount()
    }
}
