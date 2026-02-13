package nl.q42.template.navigation.deeplink

import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri
import nl.q42.template.navigation.Destination

internal val deepLinkPatterns: List<DeepLinkPattern<out Destination>> = listOf(
    DeepLinkPattern(
        uriPattern = "template://onboarding".toUri(),
        serializer = Destination.Onboarding.serializer()
    )
)

class DeeplinkParser {
    fun parseIntent(intent: Intent): Destination? {
        val uri: Uri? = intent.data
        // associate the target with the correct backstack key
        return uri?.let {
            /** STEP 2. Parse requested deeplink */
            val request = DeepLinkRequest(uri)

            /** STEP 3. Compared requested with supported deeplink to find match*/
            val match = deepLinkPatterns.firstNotNullOfOrNull { pattern ->
                DeepLinkMatcher(request, pattern).match()
            }
            /** STEP 4. If match is found, associate match to the correct key*/
            match?.let {
                //leverage kotlinx.serialization's Decoder to decode
                // match result into a backstack key
                KeyDecoder(match.args)
                    .decodeSerializableValue(match.serializer)
            }
        }
    }
}
