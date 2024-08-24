package pokitmons.pokit.data.datasource.remote.alert

import pokitmons.pokit.data.model.alert.GetAlertsResponse

interface AlertDataSource {
    suspend fun getAlerts(page: Int, size: Int): GetAlertsResponse
    suspend fun deleteAlert(alertId: Int)
}
