package nl.q42.template.domain.user.model

@JvmInline
value class EmailAddress(val value: String)

data class User(val email: EmailAddress)
