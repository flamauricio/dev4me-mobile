package com.example.dev4me.endpoints

import com.example.dev4me.dto.EmpresaResponse
import com.example.dev4me.dto.SenhaRequest
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface Empresa {
    @GET("/empresas/perfil/{id}")
    fun getEmpresaById(@Path("id")id: Int): retrofit2.Call<JsonObject?>

    @PATCH("/usuarios/senha/{id}")
    fun patchSenhaEmpresa(@Path("id") id: Int, @Body senhaRequest: SenhaRequest): Call<Unit>
}