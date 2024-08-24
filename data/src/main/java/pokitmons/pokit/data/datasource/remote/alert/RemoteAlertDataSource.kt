package pokitmons.pokit.data.datasource.remote.alert

import pokitmons.pokit.data.api.AlertApi
import pokitmons.pokit.data.model.alert.GetAlertsResponse
import javax.inject.Inject

class RemoteAlertDataSource @Inject constructor(
    private val api: AlertApi,
) : AlertDataSource {
    override suspend fun getAlerts(page: Int, size: Int): GetAlertsResponse {
        return api.getAlerts(page = page, size = size)
    }

    override suspend fun deleteAlert(alertId: Int) {
        return api.deleteAlert(alertId)
    }
}
