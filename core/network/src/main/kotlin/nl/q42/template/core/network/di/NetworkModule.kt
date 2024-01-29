package nl.q42.template.core.network.di

import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import nl.q42.template.core.network.logger.JsonFormattedHttpLogger
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
internal class NetworkModule {
    @Provides
    @Singleton
    fun providesMoshi(): Moshi = Moshi.Builder()
        .build()

    @Provides
    @Singleton
    fun providesOkhttpClient(@ConfigLogHttpCalls logHttpCalls: Boolean) = OkHttpClient.Builder()
        .apply {
            connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)

            if (logHttpCalls) {
                addInterceptor(
                    HttpLoggingInterceptor(JsonFormattedHttpLogger())
                        .apply { level = HttpLoggingInterceptor.Level.BODY }
                )
            }
        }.build()

    @Singleton
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient, moshi: Moshi, @ConfigApiMainPath apiMainPath: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(apiMainPath)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .client(httpClient)
            .build()
    }
}
