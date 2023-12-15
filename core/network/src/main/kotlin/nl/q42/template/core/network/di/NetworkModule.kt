package nl.q42.template.core.network.di

import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.q42.template.core.network.logger.JsonFormattedHttpLogger
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class NetworkModule {

    @Provides
    @Singleton
    fun providesOkhttpClient(
        @ConfigLogHttpCalls logHttpCalls: Boolean,
    ) =
        OkHttpClient.Builder()
            .apply {
                connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1, TimeUnit.MINUTES)

                if (logHttpCalls) addInterceptor(HttpLoggingInterceptor(JsonFormattedHttpLogger())
                    .apply { level = HttpLoggingInterceptor.Level.BODY })

            }.build()

    @Singleton
    @Provides
    fun provideRetrofit(
        httpClient: OkHttpClient,
        @ConfigApiMainPath apiMainPath: String,
    ): Retrofit {
        val contentType = "application/json".toMediaType()

        val json = Json { ignoreUnknownKeys = true }

        return Retrofit.Builder()
            .baseUrl(apiMainPath)
            .addConverterFactory(json.asConverterFactory(contentType))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .client(httpClient)
            .build()
    }
}
