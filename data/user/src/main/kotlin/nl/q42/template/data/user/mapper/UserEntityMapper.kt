package nl.q42.template.data.user.mapper

import nl.q42.template.data.user.local.model.UserEntity
import nl.q42.template.data.user.remote.model.UserDTO

internal fun UserDTO.mapToEntity() = UserEntity(email = args.email)
