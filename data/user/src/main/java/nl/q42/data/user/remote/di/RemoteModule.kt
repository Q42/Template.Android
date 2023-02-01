package nl.q42.data.user.remote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.q42.data.user.remote.UserApi
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class RemoteModule {

    @Provides
    @Singleton
    fun providesUserApi(
        retrofit: Retrofit,
    ): UserApi = retrofit.create(UserApi::class.java)
}
