package com.example.dev4me.endpoints

import com.example.dev4me.VagaTag
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Vaga {
    @GET("/vagas")
    fun getVagas(): Call<List<com.example.dev4me.Vaga>>

    @GET("/vagas/{id}")
    fun getVagaById(@Path("id")id: Int?): Call<VagaTag>

    @GET("vagas/all/{id}")
    fun getAllVagasById(@Path("id")id: Int?): Call<List<com.example.dev4me.Vaga>>

    @POST("/vagas")
    fun postVaga(@Body vaga: com.example.dev4me.Vaga): Call<com.example.dev4me.Vaga>

    @POST("/tags-vagas/filtros/mobile")
    fun getFilteredJobs(@Body tagList: MutableList<com.example.dev4me.Tag>): Call<List<com.example.dev4me.Vaga>>
}