package nl.q42.template.data.main.di

import nl.q42.template.data.main.UserRepositoryImpl
import nl.q42.template.data.main.local.UserLocalDataSource
import nl.q42.template.data.main.remote.MainApi
import nl.q42.template.data.main.remote.UserRemoteDataSource
import nl.q42.template.domain.main.repo.UserRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {

    single<MainApi> {
        provideRetrofitApi(get())
    }

    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
    singleOf(::UserLocalDataSource)
    singleOf(::UserRemoteDataSource)
}

internal fun provideRetrofitApi(retrofit: Retrofit): MainApi =
    retrofit.create(MainApi::class.java)