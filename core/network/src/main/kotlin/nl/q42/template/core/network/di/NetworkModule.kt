package nl.q42.template.core.network.di

import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import nl.q42.template.core.network.interceptor.UserAgentHeaderInterceptor
import nl.q42.template.core.network.logger.JsonFormattedHttpLogger
import nl.q42.template.core.utils.config.ApiMainPath
import nl.q42.template.core.utils.config.IsLogHttpCalls
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

val networkModule = module {
    singleOf(::UserAgentHeaderInterceptor)

    single<OkHttpClient> {
        provideOkHttpClient(get(), get())
    }

    single<Retrofit> {
        provideRetrofit(get(), get())
    }
}

internal fun provideOkHttpClient(
    logHttpCalls: IsLogHttpCalls,
    userAgentHeaderInterceptor: UserAgentHeaderInterceptor,
): OkHttpClient {
    return OkHttpClient.Builder()
        .apply {
            connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)

            if (logHttpCalls.value) {
                addInterceptor(
                    HttpLoggingInterceptor(JsonFormattedHttpLogger())
                        .apply { level = HttpLoggingInterceptor.Level.BODY }
                )
            }

            addInterceptor(userAgentHeaderInterceptor)
        }.build()
}

internal fun provideRetrofit(
    httpClient: OkHttpClient,
    apiMainPath: ApiMainPath,
): Retrofit {
    val contentType = "application/json".toMediaType()

    // When the server adds new fields to the response, we don't want to crash
    val json = Json { ignoreUnknownKeys = true }

    return Retrofit.Builder()
        .baseUrl(apiMainPath.value)
        .addConverterFactory(json.asConverterFactory(contentType))
        .addCallAdapterFactory(NetworkResponseAdapterFactory())
        .client(httpClient)
        .build()
}