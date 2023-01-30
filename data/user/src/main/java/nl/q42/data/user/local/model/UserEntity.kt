package nl.q42.data.user.local.model

import nl.q42.domain.user.model.User

data class UserEntity(val email: String)

fun UserEntity.mapToUser() = User(email = email)