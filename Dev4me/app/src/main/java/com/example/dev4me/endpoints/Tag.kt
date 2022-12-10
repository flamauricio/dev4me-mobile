package com.example.dev4me.endpoints

import com.example.dev4me.dto.TagVagaRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Tag {
    @GET("/tags")
    fun getTags(): Call<List<com.example.dev4me.Tag>>

    @POST("tags-vagas/{idVaga}")
    fun postTags(@Body tagaVagaRequest: TagVagaRequest, @Path("idVaga") idVaga: Integer?): Call<Unit>
}