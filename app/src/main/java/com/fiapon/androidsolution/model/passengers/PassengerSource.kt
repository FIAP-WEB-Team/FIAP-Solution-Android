/**
 * Created by LucasS
 */
package com.fiapon.androidsolution.model.passengers

import com.fiapon.androidsolution.data_context.retrofitPassengerService
import com.fiapon.androidsolution.ui.auth.RequestState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PassengerSource {

    fun createPassenger(passenger: Passenger, token: String, callback: (state: RequestState<String>) -> Unit){
        val longhand = retrofitPassengerService().setPassenger(passenger, "Bearer $token")

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