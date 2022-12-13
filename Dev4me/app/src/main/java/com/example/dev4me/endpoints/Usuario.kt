package com.example.dev4me.endpoints

import com.example.dev4me.UsuarioTag
import com.example.dev4me.dto.SenhaRequest
import com.example.dev4me.dto.UserRequest
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface Usuario {
    @GET("/usuarios/perfil/{id}")
    fun getUsuario(@Path("id") id: Int): Call<JsonObject>

    @GET("/usuarios/tags-usuario/{id}")
    fun getUsuarioTags(@Path("id") id: Int): Call<UsuarioTag>

    @GET("/usuarios")
    fun getUsers(): Call<List<UserRequest>>

    @PATCH("/usuarios/senha/{id}")
    fun patchSenhaUsuario(@Path("id") id: Int, @Body senhaRequest: SenhaRequest): Call<Unit>

    @PATCH("/usuarios/alterar-dados")
    fun patchUsuario(@Body usuario: com.example.dev4me.Usuario): Call<Unit>

    @GET("/usuarios/filter/android")
    fun getUsersByTags(@Body tagList: MutableList<com.example.dev4me.Tag>): Call<List<UserRequest>>
}