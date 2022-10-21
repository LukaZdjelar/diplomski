package com.example.diplomski_android.data.repository

import com.example.diplomski_android.model.Task

interface TaskRepository {
    fun getAll(): List<Task>
    suspend fun insert(task: Task)
}