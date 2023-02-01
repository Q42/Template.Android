package nl.q42.data.user.remote

import com.haroldadmin.cnradapter.NetworkResponse
import nl.q42.data.user.remote.model.UserDTO
import nl.q42.template.actionresult.data.ApiErrorResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface UserApi {

    /**
     * - emailForResponse is set because this test server mirrors the request as response.
     * - "get" would probably be "getUsers" on a real server.
     */
    @GET("get")
    suspend fun getUsers(@Query("email") emailForResponse: String): NetworkResponse<UserDTO, ApiErrorResponse>
}