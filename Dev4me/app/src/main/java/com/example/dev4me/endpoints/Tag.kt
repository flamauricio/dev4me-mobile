package com.example.dev4me.endpoints

import com.example.dev4me.dto.TagDto
import com.example.dev4me.dto.TagVagaRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Tag {
    @GET("/tags")
    fun getTags(): Call<List<com.example.dev4me.Tag>>

    @POST("tags-vagas/{idVaga}")
    fun postTags(@Body tagaVagaRequest: TagVagaRequest, @Path("idVaga") idVaga: Integer?): Call<Unit>

    @GET("usuarios/tags-usuario/{id}")
    fun getTagsUsuario(@Path("id")id: Int): Call<TagDto>

    @DELETE("/delete-tags-usuario/{id}")
    fun deleteUserTags(@Path("id")id: Int): Call<Unit>

    @POST("/usuarios/post-tag-usuario/{idUsuario}")
    fun postUserTags(@Path("idUsuario")id: Int, @Body tags: MutableList<com.example.dev4me.Tag>): Call<Unit>
}