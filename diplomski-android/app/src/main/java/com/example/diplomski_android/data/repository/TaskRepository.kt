package com.example.diplomski_android.data.repository

import com.example.diplomski_android.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAll(): Flow<List<Task>>
    suspend fun insert(task: Task)
    fun getByLesson(id: Long): Flow<List<Task>>
}