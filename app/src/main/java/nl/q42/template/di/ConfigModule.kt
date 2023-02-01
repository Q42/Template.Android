package nl.q42.template.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.q42.template.BuildConfig
import nl.q42.template.core.network.di.ConfigApiMainPath
import javax.inject.Singleton

/**
 * All config from the app's gradle config. Used so that other modules don't need
 * to access the BuildConfig, which has drawbacks and can cause bugs:
 * https://blog.dipien.com/stop-generating-the-buildconfig-on-your-android-modules-7d82dd7f20f1
 */
@Module
@InstallIn(SingletonComponent::class)
class ConfigModule {

    @Provides
    @Singleton
    @ConfigApiMainPath
    fun providesApiMainPath() = BuildConfig.config_api_main_url
}