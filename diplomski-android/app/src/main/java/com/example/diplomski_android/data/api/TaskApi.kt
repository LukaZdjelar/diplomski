package com.example.diplomski_android.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface TaskApi {

    @POST(value="tasks/check/{id}")
    suspend fun checkAnswer(@Path("id") id: Long, @Body answer: String): Response<Boolean>
}