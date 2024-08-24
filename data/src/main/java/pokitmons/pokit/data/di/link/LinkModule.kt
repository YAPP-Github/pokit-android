package pokitmons.pokit.data.di.link

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pokitmons.pokit.data.datasource.remote.link.LinkDataSource
import pokitmons.pokit.data.datasource.remote.link.RemoteLinkDataSource
import pokitmons.pokit.data.repository.link.LinkRepositoryImpl
import pokitmons.pokit.domain.repository.link.LinkRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LinkModule {
    @Binds
    @Singleton
    abstract fun bindLinkRepository(linkRepositoryImpl: LinkRepositoryImpl): LinkRepository

    @Binds
    @Singleton
    abstract fun bindLinkDataSource(linkDataSourceImpl: RemoteLinkDataSource): LinkDataSource
}
