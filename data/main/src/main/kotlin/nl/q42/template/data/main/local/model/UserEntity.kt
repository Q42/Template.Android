package nl.q42.template.data.main.local.model

import nl.q42.template.domain.main.model.EmailAddress
import nl.q42.template.domain.main.model.User

internal data class UserEntity(val email: String)

internal fun UserEntity.mapToUser() = User(email = EmailAddress(email))
