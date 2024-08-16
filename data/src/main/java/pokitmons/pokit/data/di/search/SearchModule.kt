package pokitmons.pokit.data.di.search

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pokitmons.pokit.data.datasource.local.search.LocalSearchWordDataSource
import pokitmons.pokit.data.datasource.local.search.SearchDataSource
import pokitmons.pokit.data.repository.search.SearchRepositoryImpl
import pokitmons.pokit.domain.repository.search.SearchRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchModule {
    @Binds
    @Singleton
    abstract fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository

    @Binds
    @Singleton
    abstract fun bindSearchDataSource(searchDataSourceImpl: LocalSearchWordDataSource): SearchDataSource
}
