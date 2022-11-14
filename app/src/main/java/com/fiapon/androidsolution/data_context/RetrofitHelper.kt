package com.fiapon.androidsolution.data_context

import com.fiapon.androidsolution.model.flights.FlightService
import com.fiapon.androidsolution.model.login.LoginService
import com.fiapon.androidsolution.model.passengers.PassengerService
import com.fiapon.androidsolution.model.tickets.TicketService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


const val BASE_URL = "https://fiapgolapi.azurewebsites.net/"

fun getRetrofitFactory(): Retrofit =
    Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        ).build()

fun retrofitFlightService(): FlightService = getRetrofitFactory().create(FlightService::class.java)
fun retrofitPassengerService(): PassengerService =
    getRetrofitFactory().create(PassengerService::class.java)

fun retrofitLoginService(): LoginService = getRetrofitFactory().create(LoginService::class.java)
fun retrofitTicketService(): TicketService = getRetrofitFactory().create(TicketService::class.java)
