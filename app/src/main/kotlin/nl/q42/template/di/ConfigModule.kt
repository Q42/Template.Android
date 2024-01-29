package nl.q42.template.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import nl.q42.template.BuildConfig
import nl.q42.template.core.network.di.ConfigApiMainPath
import nl.q42.template.core.network.di.ConfigLogHttpCalls

/**
 * All application wide config can go in here. Used so that other modules don't need to access the BuildConfig, which has drawbacks and can cause bugs:
 * https://blog.dipien.com/stop-generating-the-buildconfig-on-your-android-modules-7d82dd7f20f1
 */
@Module
@InstallIn(SingletonComponent::class)
class ConfigModule {
    @Provides
    @Singleton
    @ConfigApiMainPath
    fun providesApiMainPath(): String = BuildConfig.config_api_main_url

    @Provides
    @Singleton
    @ConfigLogHttpCalls
    fun configIsLoggingHttpCalls(): Boolean = BuildConfig.config_log_http_calls
}
