package com.example.dev4me.endpoints

import com.example.dev4me.dto.LoginRequest
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET

interface Tag {
    @GET("/tags")
    fun getTags(): Call<JsonObject>
}