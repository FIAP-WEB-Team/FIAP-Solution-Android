package com.fiapon.androidsolution.model.login

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("login")
    fun login(@Body login: Login): Call<String>
}