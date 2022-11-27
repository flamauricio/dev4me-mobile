package com.example.dev4me.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ViaCEP {
    val baseUrl = "https://viacep.com.br/ws/"
    //viacep.com.br/ws/01001000/json/

    fun getInstance(): Retrofit {
        return Retrofit
            .Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl(baseUrl).build()
    }
}