package nl.q42.template.data.user.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import nl.q42.data.user.remote.model.UserApi
import nl.q42.template.data.user.UserRepositoryImpl
import nl.q42.template.domain.user.repo.UserRepository
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal class UserDataModule {
    @Provides
    @Singleton
    fun providesUserApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
internal interface UserRepoModule {
    @Binds
    @Singleton
    fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}
