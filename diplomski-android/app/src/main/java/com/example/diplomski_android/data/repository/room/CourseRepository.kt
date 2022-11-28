package com.example.diplomski_android.data.repository.room

import com.example.diplomski_android.model.Course
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    fun getAllFlow(): Flow<List<Course>>
    fun getAll(): List<Course>
    suspend fun insert(course: Course): Long
    suspend fun insertAll(courses: List<Course>)
    fun getById(id: Long): Course
    suspend fun delete(course: Course)
    suspend fun deleteAll()
}