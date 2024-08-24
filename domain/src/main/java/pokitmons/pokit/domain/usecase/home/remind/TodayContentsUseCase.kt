package pokitmons.pokit.domain.usecase.home.remind

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.home.remind.RemindResult
import pokitmons.pokit.domain.repository.home.remind.RemindRepository
import javax.inject.Inject

class TodayContentsUseCase @Inject constructor(private val remindRepository: RemindRepository) {
    suspend fun getTodayContents(): PokitResult<List<RemindResult>> {
        return remindRepository.getTodayContents()
    }
}
