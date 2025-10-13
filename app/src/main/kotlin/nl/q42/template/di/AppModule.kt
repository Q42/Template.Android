package nl.q42.template.di

import nl.q42.template.MainApplication
import nl.q42.template.core.network.di.networkModule
import nl.q42.template.data.main.di.dataModule
import nl.q42.template.domain.main.di.domainModule
import nl.q42.template.home.di.homeModule
import nl.q42.template.navigation.di.navigationModule
import nl.q42.template.onboarding.di.onboardingModule
import nl.q42.template.ui.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

fun initDependencyInjection(application: MainApplication) {
    startKoin {
        androidLogger()
        androidContext(application)
        modules(appModule)
    }
}

val appModule = module {
    includes(
        configModule,

        // features
        homeModule,
        onboardingModule,

        // core
        networkModule,
        navigationModule,
        presentationModule,

        // data
        dataModule,

        // domain
        domainModule
    )
}