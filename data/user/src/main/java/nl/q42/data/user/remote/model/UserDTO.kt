package nl.q42.data.user.remote.model

import nl.q42.data.user.local.model.UserEntity

data class UserDTO(val email: String)

fun UserDTO.mapToEntity() = UserEntity(email = email)