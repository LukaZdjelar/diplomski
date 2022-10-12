package com.example.diplomski_android.data.api

import com.example.diplomski_android.model.Course
import retrofit2.Response
import retrofit2.http.GET

interface CourseApi {

    @GET(value = "courses")
    suspend fun getCourses(): Response<List<Course>>
}