package com.tengtonghann.android.androidmvvm.data.repositories

import com.tengtonghann.android.androidmvvm.data.db.AppDatabase
import com.tengtonghann.android.androidmvvm.data.db.entities.User
import com.tengtonghann.android.androidmvvm.data.network.MyApi
import com.tengtonghann.android.androidmvvm.data.network.SafeApiRequest
import com.tengtonghann.android.androidmvvm.data.network.response.AuthResponse

class UserRepository(
    private val api: MyApi,
    private val db: AppDatabase
): SafeApiRequest() {

    suspend fun userLogin(email: String, password: String): AuthResponse {
        return apiRequest { api.userLogin(email, password) }
//        return MyApi().userLogin(email, password)
    }

    suspend fun userSignup(name: String, email: String, password: String) : AuthResponse{
        return apiRequest { api.userSignup(name, email, password) }
    }

    suspend fun saveUser(user: User) = db.getUserDao().insert(user)

    fun getUser() = db.getUserDao().getUser()

    /*
    fun userLogin(email: String, password: String): LiveData<String> {

        val loginResponse = MutableLiveData<String>()

        MyApi().userLogin(email, password)
            .enqueue(object: Callback<ResponseBody> {

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    loginResponse.value = t.message
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        loginResponse.value = response.body()?.string()
                    } else {
                        loginResponse.value = response.errorBody()?.string()
                    }
                }

            })

        return loginResponse
    }
     */

}