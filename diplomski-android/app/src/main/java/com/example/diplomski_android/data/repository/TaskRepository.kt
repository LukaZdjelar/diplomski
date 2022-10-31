package com.example.diplomski_android.data.repository

import com.example.diplomski_android.model.Lesson
import com.example.diplomski_android.model.Task

interface TaskRepository {
    fun getAll(): List<Task>
    suspend fun insert(task: Task)
    fun getByLesson(id: Long): List<Task>
}