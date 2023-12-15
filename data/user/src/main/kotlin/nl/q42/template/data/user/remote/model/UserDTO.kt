package nl.q42.template.data.user.remote.model

import kotlinx.serialization.Serializable


/**
 * Data Transfer Models (DTO) are preferably generated from server source code/json/schemas and do not contain
 * any changes compared to the server contract.
 */

@Serializable
data class UserDTO(
    val args: ArgsDTO
)

@Serializable
data class ArgsDTO(
    val email: String
)
