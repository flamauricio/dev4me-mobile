package com.example.dev4me.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Rest {
    val baseUrl = "http://44.209.230.38:8080/"

    fun getInstance(): Retrofit {
        return Retrofit
            .Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl(baseUrl).build()
    }
}