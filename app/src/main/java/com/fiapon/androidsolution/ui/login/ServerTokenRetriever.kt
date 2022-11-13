/**
 * Created by LucasS
 */
package com.fiapon.androidsolution.ui.login

import com.fiapon.androidsolution.data_context.retrofitLoginService
import com.fiapon.androidsolution.model.login.Login
import com.fiapon.androidsolution.ui.auth.RequestState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServerTokenRetriever {
    fun requestAccessToken(callback: (state: RequestState<String>) -> Unit) {
        val loginAccess = Login("FiapTrabalho", "153020@10gH")
        val longhand = retrofitLoginService().login(loginAccess)

        longhand.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.body() != null) {
                    callback(RequestState.Success(response.body().toString()))
                } else {
                    callback(RequestState.Error(Error("Null body message received from token request")))
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callback(RequestState.Error(t))
            }

        })
    }
}