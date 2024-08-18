package pokitmons.pokit.data.repository.alert

import pokitmons.pokit.data.datasource.remote.alert.AlertDataSource
import pokitmons.pokit.data.mapper.alert.AlertMapper
import pokitmons.pokit.data.model.common.parseErrorResult
import pokitmons.pokit.domain.commom.PokitResult
import pokitmons.pokit.domain.model.alert.Alarm
import pokitmons.pokit.domain.repository.alert.AlertRepository
import javax.inject.Inject

class AlertRepositoryImpl @Inject constructor(
    private val dataSource: AlertDataSource,
) : AlertRepository {
    override suspend fun getAlerts(page: Int, size: Int): PokitResult<List<Alarm>> {
        return runCatching {
            val response = dataSource.getAlerts(page = page, size = size)
            val mappedResponse = AlertMapper.mapperToAlarmList(response)
            PokitResult.Success(result = mappedResponse)
        }.getOrElse { throwable ->
            parseErrorResult(throwable)
        }
    }

    override suspend fun deleteAlert(alertId: Int): PokitResult<Unit> {
        return runCatching {
            dataSource.deleteAlert(alertId)
            PokitResult.Success(result = Unit)
        }.getOrElse { throwable ->
            parseErrorResult(throwable)
        }
    }
}
