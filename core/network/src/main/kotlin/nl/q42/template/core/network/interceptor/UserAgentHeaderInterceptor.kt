package nl.q42.template.core.network.interceptor

import android.os.Build
import nl.q42.template.core.utils.di.ConfigAppVersionCode
import nl.q42.template.core.utils.di.ConfigAppVersionName
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
    @param:ConfigAppVersionName private val appVersionName: String,
    @param:ConfigAppVersionCode private val appVersionCode: Int,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .apply {
                val androidVersionRelease = Build.VERSION.RELEASE
                val userAgentString = "Template/$appVersionName ($appVersionCode; Android/$androidVersionRelease; ${Build.BRAND} ${Build.MODEL})"
                header("User-Agent", userAgentString)
            }
            .build()
        return chain.proceed(request)
    }
}
