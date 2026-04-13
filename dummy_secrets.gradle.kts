extra["signingSecrets"] = mapOf(
    "upload" to mapOf(
        "storePassword" to "",
        "keyAlias" to "",
        "keyPassword" to "",
    )
    // Additional signing configs can be added here
)

extra["apiKeys"] = mapOf(
    // additional project secrets like API keys can be added here
    "dev" to mapOf<String, String>(),
    "prod" to mapOf<String, String>(),
)
