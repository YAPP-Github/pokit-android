package pokitmons.pokit.data.di.alert

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pokitmons.pokit.data.datasource.remote.alert.AlertDataSource
import pokitmons.pokit.data.datasource.remote.alert.RemoteAlertDataSource
import pokitmons.pokit.data.repository.alert.AlertRepositoryImpl
import pokitmons.pokit.domain.repository.alert.AlertRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AlertModule {
    @Binds
    @Singleton
    abstract fun bindAlertRepository(alertRepositoryImpl: AlertRepositoryImpl): AlertRepository

    @Binds
    @Singleton
    abstract fun bindAlertDataSource(alertDataSourceImpl: RemoteAlertDataSource): AlertDataSource
}
