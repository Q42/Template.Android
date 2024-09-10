package nl.q42.template.data.user.local.model

import nl.q42.template.domain.user.model.EmailAddress
import nl.q42.template.domain.user.model.User

internal data class UserEntity(val email: String)

internal fun UserEntity.mapToUser() = User(email = EmailAddress(email))
