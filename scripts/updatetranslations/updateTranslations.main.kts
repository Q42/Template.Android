#!/usr/bin/env kotlin
/**
 * This script downloads all the Android string translations
 * from the PoEditor API (https://poeditor.com/docs/api#projects_export)
 */

@file:DependsOn("org.json:json:20230227")
@file:DependsOn("com.squareup.okhttp3:okhttp:4.11.0")

import org.json.JSONObject
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import kotlin.script.experimental.dependencies.DependsOn
import kotlin.system.exitProcess

val POEDITOR_API_TOKEN = "set api token here"
val POEDITOR_PROJECT_ID = "set project id here"

val resourceFolder = "./core/ui/src/main/res"

// Map containing pairs of <language, translation-file-path>
val LANGUAGES = mapOf(
    "en-gb" to listOf(
        "$resourceFolder/values/strings.xml",
        "$resourceFolder/values-en/strings.xml"
    ),
    "nl" to listOf("$resourceFolder/values-nl/strings.xml"),
)

val POEDITOR_API_ENDPOINT = "https://api.poeditor.com/v2/projects/export"

// This defines the platform's format of the exported translation file
// Android = android_strings / iOS = apple_strings
val EXPORT_TYPE = "android_strings"

// Running this flag while translations are missing stops the execution
val NO_MISSING_TRANSLATIONS_FLAG = "-noMissingTranslations"

val noMissingTranslations = args.getOrNull(0) == NO_MISSING_TRANSLATIONS_FLAG

if (noMissingTranslations) {
    println("Missing translations are NOT allowed.")
} else {
    println("Missing translations are allowed.")
}

// Note that we duplicate the default to /values-nl/ because without it nl-NL would default to /values-nl-rBE/ instead of /values/.
// More details: https://stackoverflow.com/questions/69379425/android-os-language-lookup-let-os-know-what-the-apps-default-language-is
for ((language, outputFiles) in LANGUAGES) {

    outputFiles.forEach { outputFile ->
        File(outputFile).parentFile.mkdirs()
    }

    val requestParams = mapOf(
        "api_token" to POEDITOR_API_TOKEN,
        "id" to POEDITOR_PROJECT_ID,
        "language" to language,
        "type" to EXPORT_TYPE,
        "order" to "terms"
    ).entries.joinToString("&") { (key, value) ->
        "${URLEncoder.encode(key, "UTF-8")}=${URLEncoder.encode(value, "UTF-8")}"
    }

    // Send post request to get download URL
    val connection = URL(POEDITOR_API_ENDPOINT).openConnection() as HttpURLConnection
    connection.apply {
        requestMethod = "POST"
        doOutput = true
        setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
        outputStream.write(requestParams.toByteArray())
    }

    val response = connection.inputStream.bufferedReader().readText()
    val responseJson = JSONObject(response)
    println(responseJson)
    val downloadUrl = responseJson.getJSONObject("result").getString("url")

    // Download translation file
    var content = URL(downloadUrl).readText()

    // Check for empty strings and stop if the missing translations flag is enabled
    // and there are missing translations
    val emptyStringsRegex = Regex("""".*><""")
    val emptyStrings = emptyStringsRegex.findAll(content).map { it.value }.toList()

    if (emptyStrings.isNotEmpty() && noMissingTranslations) {
        println("ERROR: Translations for language $language contains the following empty (untranslated) strings:")
        emptyStrings.forEach { println(it.removeSuffix("><")) }
        exitProcess(1)
    }

    // Remove empty strings. Android will fall back to the base language for these.
    // If the current language is the base language, the string will be removed from the project,
    // and the project won't compile. This is fine since the base language should not have empty strings.
    content = content.replace(Regex("""<string name=".*"></string>"""), "")

    // Replace all dots in names/keys with underscores, android does not support dots:
    content = content.replace(Regex("""name="(.+?)"""")) { matchResult ->
        val newName = matchResult.groupValues[1].replace(".", "_")
        """name="$newName""""
    }

    // Replace all %@'s (iOS replacement param) with the android %s string replacement param
    content = content.replace("%@", "%s")

    // Remove server specific strings
    content = content.replace(Regex("""<string name="server_.*">[\s\S]*?</string>"""), "")
    content = content.replace(Regex("""<plurals name="server_.*">[\s\S]*?</plurals>"""), "")

    // Remove iOS specific strings
    content = content.replace(Regex("""<string name=".*_ios">[\s\S]*?</string>"""), "")
    content = content.replace(Regex("""<plurals name=".*_ios">[\s\S]*?</plurals>"""), "")

    // Remove comments
    content = content.replace(Regex("""<!--(.*?)-->\n""", RegexOption.DOT_MATCHES_ALL), "")

    // Remove double empty lines
    content = content.replace(Regex("""\n\s*\n"""), "\n\n")

    // Remove double and triple tabs
    content = content.replace("        <string", "    <string")
    content = content.replace("        <string", "    <string")

    // Write/overwrite downloaded file to disk
    for (outputFile in outputFiles) {
        println("    Writing to file $outputFile")
        File(outputFile).writeText(content)
    }
}

println("Finished downloading translations")

