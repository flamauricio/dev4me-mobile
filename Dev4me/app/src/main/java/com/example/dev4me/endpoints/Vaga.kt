package com.example.dev4me.endpoints

import com.example.dev4me.dto.JobRequest
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET

interface Vaga {
    @GET("/vagas")
    fun getVagas(): Call<List<JobRequest>>
}