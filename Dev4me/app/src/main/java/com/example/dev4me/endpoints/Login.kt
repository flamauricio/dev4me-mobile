package com.example.dev4me.endpoints

import com.example.dev4me.dto.LoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Login {

    @POST("/usuarios/login")
    fun logarUsuario(@Body body: LoginRequest): Call<Int>

    @POST("/empresas/login")
    fun logarEmpresa(@Body body: LoginRequest): Call<Int>
}