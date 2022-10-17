package com.example.dev4me.endpoints

import com.example.dev4me.dto.EmpresaRequest
import com.example.dev4me.dto.UsuarioRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Cadastro {
    @POST("/empresas")
    fun postEmpresa(@Body body: EmpresaRequest): Call<Unit>

    @POST("/usuarios")
    fun postUsuario(@Body body: UsuarioRequest): Call<Unit>
}