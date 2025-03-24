package nl.q42.template.core.network.interceptor

import nl.q42.template.core.network.di.ConfigAppVersionCode
import nl.q42.template.core.network.di.ConfigAppVersionName
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Adds a user agent with app name, app version, app versionCode, os version
 * which can be useful for request logging and debugging.
  */
@Singleton
class UserAgentHeaderInterceptor @Inject constructor(
    @ConfigAppVersionName private val appVersionName: String,
    @ConfigAppVersionCode private val appVersionCode: Int,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .apply {
                val androidVersionRelease = android.os.Build.VERSION.RELEASE
                val userAgentString = "Template/$appVersionName ($appVersionCode; Android $androidVersionRelease)"
                header("User-Agent", userAgentString)
            }
            .build()
        return chain.proceed(request)
    }
}
