package cz.muni.takemytext.webservice

import cz.muni.takemytext.webservice.response.UsersResponse
import retrofit2.Call
import retrofit2.http.GET

interface UserWebservice {

    @GET("/users")
    fun getUsers(): Call<List<UsersResponse>>
}