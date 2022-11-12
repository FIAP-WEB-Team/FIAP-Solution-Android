package com.fiapon.androidsolution.model.tickets

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface TicketService {
    @POST("settickets")
    fun setTickets(@Body ticket: Ticket, @Header("Authorization")authorization:String): Call<String>

    @GET("gettickets")
    fun getTickets(@Header("Authorization")authorization:String): Call<List<Ticket>>
}