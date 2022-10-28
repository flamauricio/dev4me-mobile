package com.example.dev4me.endpoints

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface Usuario {
    @GET("/usuarios/perfil/{id}")
    fun getUsuario(@Path("id") id: Int): Call<JsonObject>

    @PATCH("/usuarios/senha/{id}")
    fun patchSenhaUsuario(@Path("id") id: Int): Call<Unit>
}