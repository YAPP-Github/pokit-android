package pokitmons.pokit.data.di.setting

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pokitmons.pokit.data.datasource.remote.auth.AuthDataSource
import pokitmons.pokit.data.datasource.remote.auth.RemoteAuthDataSourceImpl
import pokitmons.pokit.data.datasource.remote.setting.RemoteSettingDataSourceImpl
import pokitmons.pokit.data.datasource.remote.setting.SettingDataSource
import pokitmons.pokit.data.repository.auth.AuthRepositoryImpl
import pokitmons.pokit.data.repository.setting.SettingRepositoryImpl
import pokitmons.pokit.domain.repository.auth.AuthRepository
import pokitmons.pokit.domain.repository.setting.SettingRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingModule {
    @Binds
    @Singleton
    abstract fun bindSettingRepository(settingRepositoryImpl: SettingRepositoryImpl): SettingRepository

    @Binds
    @Singleton
    abstract fun bindSettingDataSource(settingDataSourceImpl: RemoteSettingDataSourceImpl): SettingDataSource
}
