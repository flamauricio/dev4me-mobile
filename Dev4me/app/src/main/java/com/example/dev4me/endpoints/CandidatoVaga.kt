package com.example.dev4me.endpoints

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CandidatoVaga {
    @GET("/candidatos/{idEmpresa}")
    fun getCandidatosVagas(@Path("idEmpresa") idEmpresa: Int): Call<List<com.example.dev4me.CandidatoVaga>>

    @POST("/candidatos/{idVaga}/{idUsuario}")
    fun postApplication(@Path("idVaga") idVaga: Int, @Path("idUsuario") id: Int): Call<Unit>
}