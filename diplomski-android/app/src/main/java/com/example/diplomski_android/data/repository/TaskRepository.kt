package com.example.diplomski_android.data.repository

import com.example.diplomski_android.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllFlow(): Flow<List<Task>>
    fun getAll(): List<Task>
    suspend fun insert(task: Task)
    fun getByLessonFlow(id: Long): Flow<List<Task>>
    fun getByLesson(id: Long): List<Task>
    suspend fun delete(task: Task)
    suspend fun deleteByLesson(lessonId: Long)
    fun countByLesson(lessonId: Long): Int
}