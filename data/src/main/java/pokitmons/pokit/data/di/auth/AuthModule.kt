package pokitmons.pokit.data.di.auth

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pokitmons.pokit.data.datasource.remote.auth.AuthDataSource
import pokitmons.pokit.data.datasource.remote.auth.RemoteAuthDataSourceImpl
import pokitmons.pokit.data.repository.auth.AuthRepositoryImpl
import pokitmons.pokit.domain.repository.auth.AuthRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindAuthDataSource(authDataSourceImpl: RemoteAuthDataSourceImpl): AuthDataSource
}
