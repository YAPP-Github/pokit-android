package pokitmons.pokit.data.di.home.remind

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pokitmons.pokit.data.datasource.remote.home.remind.RemindDataSource
import pokitmons.pokit.data.datasource.remote.home.remind.RemindDataSourceImpl
import pokitmons.pokit.data.datasource.remote.link.RemoteLinkDataSource
import pokitmons.pokit.data.repository.home.remind.RemindRepositoryImpl
import pokitmons.pokit.data.repository.link.LinkRepositoryImpl
import pokitmons.pokit.domain.repository.home.remind.RemindRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemindModule {
    @Binds
    @Singleton
    abstract fun bindRemindRepository(remindRepositoryImpl: RemindRepositoryImpl): RemindRepository

    @Binds
    @Singleton
    abstract fun bindRemindDataSource(remindDataSourceImpl: RemindDataSourceImpl): RemindDataSource
}
