/**
 * Created by LucasS
 */
package com.fiapon.androidsolution.model.tickets

import com.fiapon.androidsolution.data_context.retrofitTicketService
import com.fiapon.androidsolution.ui.auth.RequestState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TicketSource {
    fun createTickets(ticket: Ticket, token: String, callback: (state: RequestState<String>) -> Unit){
        val longhand = retrofitTicketService().setTickets(ticket, "Bearer $token")

        longhand.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.body() != null) {
                    callback(RequestState.Success(response.body().toString()))
                } else {
                    callback(RequestState.Error(Error(response.message().toString())))
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callback(RequestState.Error(t))
            }

        })
    }
}