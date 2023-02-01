package nl.q42.data.user.remote.model

import com.squareup.moshi.JsonClass
import nl.q42.data.user.local.model.UserEntity

@JsonClass(generateAdapter = true)
internal data class UserDTO(
    val args: ArgsDTO
)

@JsonClass(generateAdapter = true)
internal data class ArgsDTO(
    val email: String
)

internal fun UserDTO.mapToEntity() = UserEntity(email = args.email)
