package pokitmons.pokit.domain.usecase.alert

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.repository.alert.AlertRepository
import javax.inject.Inject

class DeleteAlertUseCase @Inject constructor(
    private val repository: AlertRepository,
) {
    suspend fun deleteAlert(alertId: Int): PokitResult<Unit> {
        return repository.deleteAlert(alertId)
    }
}
