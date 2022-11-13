package com.fiapon.androidsolution.model.flights

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface FlightService {
    @POST("setFlight")
    fun setFlights(
        @Body flight: Flight,
        @Header("Authorization") authorization: String
    ): Call<String>

    @GET("getFlights")
    fun getFlights(@Header("Authorization") authorization: String): Call<List<Flight>>
}