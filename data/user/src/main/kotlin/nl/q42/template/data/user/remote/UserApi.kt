package nl.q42.data.user.remote.model

import com.haroldadmin.cnradapter.NetworkResponse
import nl.q42.template.actionresult.data.ApiErrorResponse
import nl.q42.template.data.user.remote.model.UserDTO
import retrofit2.http.GET
import retrofit2.http.Query

internal interface UserApi {
    /**
     * note: [dummyEmailForResponse] is set because this test server mirrors the request as response.
     */
    @GET("get")
    suspend fun getUsers(@Query("email") dummyEmailForResponse: String): NetworkResponse<UserDTO, ApiErrorResponse>
}
