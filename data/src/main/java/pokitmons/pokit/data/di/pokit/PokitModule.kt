package pokitmons.pokit.data.di.pokit

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pokitmons.pokit.data.datasource.remote.pokit.PokitDataSource
import pokitmons.pokit.data.datasource.remote.pokit.RemotePokitDataSource
import pokitmons.pokit.data.repository.pokit.PokitRepositoryImpl
import pokitmons.pokit.domain.repository.pokit.PokitRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PokitModule {
    @Binds
    @Singleton
    abstract fun bindPokitRepository(pokitRepositoryImpl: PokitRepositoryImpl): PokitRepository

    @Binds
    @Singleton
    abstract fun bindPokitDataSource(pokitDataSourceImpl: RemotePokitDataSource): PokitDataSource
}
