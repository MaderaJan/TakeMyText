package cz.muni.takemytext.repository

import cz.muni.takemytext.webservice.RetrofiUtil
import cz.muni.takemytext.webservice.response.UsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    private val gitHubWebService by lazy { RetrofiUtil.createWebService() }

    fun fetchUsers(callback: (List<String>?) -> Unit) {
        gitHubWebService.getUsers().enqueue(object: Callback<List<UsersResponse>>{
            override fun onFailure(call: Call<List<UsersResponse>>, t: Throwable) {}

            override fun onResponse(call: Call<List<UsersResponse>>, response: Response<List<UsersResponse>>) {
                if (response.isSuccessful) {
                    val names = response.body()?.map { it.name }
                    callback.invoke(names)
                }
            }
        })
    }
}