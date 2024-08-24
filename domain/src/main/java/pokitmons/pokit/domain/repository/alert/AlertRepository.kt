package pokitmons.pokit.domain.repository.alert

import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.alert.Alarm

interface AlertRepository {
    suspend fun getAlerts(page: Int, size: Int): PokitResult<List<Alarm>>
    suspend fun deleteAlert(alertId: Int): PokitResult<Unit>
}
