package nl.q42.data.user.remote.model

import nl.q42.data.user.local.model.UserEntity

internal data class UserDTO(val email: String)

internal fun UserDTO.mapToEntity() = UserEntity(email = email)
