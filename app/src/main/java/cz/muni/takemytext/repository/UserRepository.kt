package cz.muni.takemytext.repository

import cz.muni.takemytext.webservice.RetrofitUtil
import cz.muni.takemytext.webservice.response.UsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    private val usersWebService by lazy { RetrofitUtil.createWebService() }

    fun fetchUsers(callback: (List<String>?) -> Unit) {
        usersWebService.getUsers().enqueue(object: Callback<List<UsersResponse>>{
            override fun onFailure(call: Call<List<UsersResponse>>, t: Throwable) {
                callback.invoke(null)
            }

            override fun onResponse(call: Call<List<UsersResponse>>, response: Response<List<UsersResponse>>) {
                if (response.isSuccessful) {
                    val names = response.body()?.map { it.name }
                    callback.invoke(names)
                }
            }
        })
    }
}