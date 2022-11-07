package com.example.diplomski_android.data.repository

import com.example.diplomski_android.model.Course
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    fun getAll(): Flow<List<Course>>
    suspend fun insert(course: Course)
    fun getById(id: Long): Course
}