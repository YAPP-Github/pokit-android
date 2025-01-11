package pokitmons.pokit.data.di.shared

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pokitmons.pokit.data.datasource.remote.shared.SharedDataSource
import pokitmons.pokit.data.datasource.remote.shared.SharedDataSourceImpl
import pokitmons.pokit.data.repository.shared.SharedRepositoryImpl
import pokitmons.pokit.domain.repository.shared.SharedRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SharedModule {
    @Binds
    @Singleton
    abstract fun bindSharedRepository(sharedRepositoryImpl: SharedRepositoryImpl): SharedRepository

    @Binds
    @Singleton
    abstract fun bindSharedDataSource(sharedDataSourceImpl: SharedDataSourceImpl): SharedDataSource
}
