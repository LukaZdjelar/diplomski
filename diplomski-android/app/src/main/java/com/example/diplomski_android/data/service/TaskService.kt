package com.example.diplomski_android.data.service

import com.example.diplomski_android.data.retrofit.RetrofitInstance

class TaskService {

    suspend fun checkAnswer(id: Long, answer: String): Boolean? {
        val response = RetrofitInstance.taskApi.checkAnswer(id, answer)
        return response.body()
    }
}