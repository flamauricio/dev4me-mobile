package com.example.dev4me.endpoints

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Vaga {
    @GET("/vagas")
    fun getVagas(): Call<List<com.example.dev4me.Vaga>>

    @POST("/vagas")
    fun postVaga(@Body vaga: com.example.dev4me.Vaga): Call<com.example.dev4me.Vaga>
}