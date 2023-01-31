package nl.q42.data.user.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.q42.data.UserRepository
import nl.q42.data.user.UserRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
internal interface UserModule {
    @Binds
    fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

}
