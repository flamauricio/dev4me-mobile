package com.example.dev4me.endpoints

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Vaga {
    @GET("/vagas")
    fun getVagas(@Path("idVaga") id: Int): Call<JsonObject>
}