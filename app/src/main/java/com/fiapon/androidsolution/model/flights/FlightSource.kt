/**
 * Created by LucasS
 */
package com.fiapon.androidsolution.model.flights

import com.fiapon.androidsolution.data_context.retrofitFlightService
import com.fiapon.androidsolution.ui.auth.RequestState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FlightSource {
    val flightDummyData = mutableListOf<Flight>(
        Flight(
            12344,
            "Budapeste",
            "2022-11-01T12:00:00.000",
            "Barcelona",
            "2022-11-01T14:00:00.000",
            2100.32
        ),
        Flight(
            34419,
            "São Paulo (GRU)",
            "2022-10-01T13:30:00.000",
            "Rio de Janeiro (SDU)",
            "2022-10-01T15:00:00.000",
            320.54
        ),
        Flight(
            9622,
            "São Paulo (CGH)",
            "2022-11-26T09:20:00.000",
            "Nova York",
            "2022-11-26T15:10:00.000",
            2134.1
        ),
    )

    /**
     * Returns all available flights in the api
     */
    fun getAllFlights(token: String, callback: (state: RequestState<ArrayList<Flight>>) -> Unit){
        val longhand = retrofitFlightService().getFlights("Bearer $token")

        longhand.enqueue(object : Callback<List<Flight>> {
            override fun onResponse(call: Call<List<Flight>>, response: Response<List<Flight>>) {
                if (response.body() != null) {
                    callback(RequestState.Success(response.body() as ArrayList<Flight>))
                } else {
                    callback(RequestState.Error(Error("Null body message received from token request")))
                }
            }

            override fun onFailure(call: Call<List<Flight>>, t: Throwable) {
                callback(RequestState.Error(t))
            }

        })
    }
}