package pokitmons.pokit.domain.usecase.alert

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.alert.Alarm
import pokitmons.pokit.domain.repository.alert.AlertRepository
import javax.inject.Inject

class GetAlertsUseCase @Inject constructor(
    private val repository: AlertRepository,
) {
    suspend fun getAlerts(page: Int, size: Int): PokitResult<List<Alarm>> {
        return repository.getAlerts(page = page, size = size)
    }
}
