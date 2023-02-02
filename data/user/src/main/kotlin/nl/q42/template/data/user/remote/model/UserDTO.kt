package nl.q42.template.data.user.remote.model

import com.squareup.moshi.JsonClass
import nl.q42.template.data.user.local.model.UserEntity

/**
 * Data Transfer Models (DTO) are preferably generated from server source code/json/schemas and do not contain
 * any changes compared to the server contract.
 */

@JsonClass(generateAdapter = true)
internal data class UserDTO(
    val args: ArgsDTO
)

@JsonClass(generateAdapter = true)
internal data class ArgsDTO(
    val email: String
)

internal fun UserDTO.mapToEntity() = UserEntity(email = args.email)
