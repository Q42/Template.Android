package nl.q42.template.data.main.mapper

import nl.q42.template.data.main.local.model.UserEntity
import nl.q42.template.data.main.remote.model.UserDTO

internal fun UserDTO.mapToEntity() = UserEntity(email = args.email)
