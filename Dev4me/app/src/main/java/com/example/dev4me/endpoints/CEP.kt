package com.example.dev4me.endpoints

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CEP {
    @GET("/{cep}/json")
    fun getCEP(@Path("cep") id: Int): Call<JsonObject>
}