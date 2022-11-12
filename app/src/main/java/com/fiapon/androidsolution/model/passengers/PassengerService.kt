package com.fiapon.androidsolution.model.passengers

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface PassengerService {
    @POST("setPassenger")
    fun setPassenger(
        @Body passenger: Passenger,
        @Header("Authorization") authorization: String
    ): Call<String>

    @GET("getPassenger")
    fun getPassenger(@Header("Authorization") authorization: String): Call<List<Passenger>>

}