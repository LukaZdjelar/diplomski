package com.example.diplomski_android.data.retrofit

import com.example.diplomski_android.data.api.ChapterApi
import com.example.diplomski_android.data.api.CourseApi
import com.example.diplomski_android.data.api.TaskApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val baseUrl: String = "http://10.0.2.2:8080/api/"

    val courseApi: CourseApi by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CourseApi::class.java)
    }

    val chapterApi: ChapterApi by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ChapterApi::class.java)
    }

    val taskApi: TaskApi by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TaskApi::class.java)
    }
}