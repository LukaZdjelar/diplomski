package com.example.diplomski_android.api

import com.example.diplomski_android.model.Chapter
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ChapterApi {

    @GET(value = "chapters/{id}")
    suspend fun getChaptersByCourse(@Path("id") id: String): Response<List<Chapter>>
}