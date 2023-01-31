package nl.q42.data.user.local.model

import nl.q42.data.user.models.User

internal data class UserEntity(val email: String)

internal fun UserEntity.mapToUser() = User(email = email)
