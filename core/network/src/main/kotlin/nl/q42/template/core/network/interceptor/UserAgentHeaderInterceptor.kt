package nl.q42.template.core.network.interceptor

import android.os.Build
import nl.q42.template.core.utils.config.AppVersionCode
import nl.q42.template.core.utils.config.AppVersionName
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Adds a user agent with app name, app version, app versionCode, os version
 * which can be useful for request logging and debugging.
 */
class UserAgentHeaderInterceptor(
    private val appVersionName: AppVersionName,
    private val appVersionCode: AppVersionCode,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .apply {
                val androidVersionRelease = Build.VERSION.RELEASE
                val userAgentString =
                    "Template/${appVersionName.value} (${appVersionCode.value}; Android/$androidVersionRelease; ${Build.BRAND} ${Build.MODEL})"
                header("User-Agent", userAgentString)
            }
            .build()
        return chain.proceed(request)
    }
}
